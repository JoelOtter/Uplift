#!/usr/bin/python
from gi.repository import Gtk
import json

questsAddress = "Uplift-android/assets/quests.upl"

class NewQuestDialog(Gtk.Dialog):

    def __init__(self, parent):
        Gtk.Dialog.__init__(self, "New Quest", parent, 0,
            (Gtk.STOCK_CANCEL, Gtk.ResponseType.CANCEL,
             Gtk.STOCK_OK, Gtk.ResponseType.OK))
        box = self.get_content_area()
        keyBox = Gtk.Box(spacing=2)
        keyLabel = Gtk.Label("Key")
        self.keyEntry = Gtk.Entry()
        nameBox = Gtk.Box(spacing=2)
        nameLabel = Gtk.Label("Name")
        self.nameEntry = Gtk.Entry()
        keyBox.pack_start(keyLabel, False, False, 2)
        keyBox.pack_start(self.keyEntry, True, True, 2)
        nameBox.pack_start(nameLabel, False, False, 2)
        nameBox.pack_start(self.nameEntry, True, True, 2)
        box.pack_start(keyBox, False, False, 0)
        box.pack_start(nameBox, False, False, 0)
        self.show_all()

class NewTaskDialog(Gtk.Dialog):

    def __init__(self, parent):
        Gtk.Dialog.__init__(self, "New task", parent, 0,
            (Gtk.STOCK_CANCEL, Gtk.ResponseType.CANCEL,
             Gtk.STOCK_OK, Gtk.ResponseType.OK))
        box = self.get_content_area()
        numBox = Gtk.Box(spacing=2)
        numLabel = Gtk.Label("#")
        self.numEntry = Gtk.Entry()
        textBox = Gtk.Box(spacing=2)
        textLabel = Gtk.Label("Text")
        self.textEntry = Gtk.Entry()
        numBox.pack_start(numLabel, False, False, 2)
        numBox.pack_start(self.numEntry, True, True, 2)
        textBox.pack_start(textLabel, False, False, 2)
        textBox.pack_start(self.textEntry, True, True, 2)
        box.pack_start(numBox, False, False, 0)
        box.pack_start(textBox, False, False, 0)
        self.show_all()

class QuestWindow(Gtk.Window):

    key = ""
    num = 0
    name = ""
    tasks = []
    selectedTask = []

    def __init__(self):
        Gtk.Window.__init__(self, title="Uplift Quest Builder")
        self.set_size_request(800, 500)
        self.set_default_icon_from_file("Uplift-android/res/drawable-xhdpi/ic_launcher.png")
        self.box = Gtk.Box(spacing=0)
        self.add(self.box)

        self.toolbar = Gtk.Toolbar()
        self.toolbar.set_style(Gtk.ToolbarStyle.BOTH_HORIZ)
        self.saveButton = Gtk.ToolButton(stock_id=Gtk.STOCK_SAVE, label="Save")
        self.saveButton.set_expand(True)
        self.saveButton.connect("clicked", self.update_file)
        self.toolbar.add(self.saveButton)
        self.newButton = Gtk.ToolButton(stock_id=Gtk.STOCK_NEW, label="New Quest")
        self.newButton.set_expand(True)
        self.newButton.connect("clicked", self.new_quest)
        self.toolbar.add(self.newButton)

        store = self.create_model()
        self.treeView = Gtk.TreeView(store)
        self.treeView.set_rules_hint(True)
        self.create_columns(self.treeView)
        sw = Gtk.ScrolledWindow()
        sw.set_size_request(200, 0)
        self.box.pack_start(sw, False, True, 0)
        sw.add(self.treeView)
        self.treeView.get_selection().connect("changed", self.on_tree_selection_changed)

        self.infobox = Gtk.Box(orientation=Gtk.Orientation.VERTICAL,spacing=5)
        self.infoscroll = Gtk.ScrolledWindow()
        self.infoinner = Gtk.Box(orientation=Gtk.Orientation.VERTICAL, spacing=5)
        self.infoTitle = Gtk.Label("Title")
        self.infoTitle.set_alignment(0, 0)

        #Tasks
        self.taskBox = Gtk.Box(spacing=5)
        self.taskLabel = Gtk.Label("<b>Tasks</b>")
        self.taskLabel.set_use_markup(True)
        self.taskLabel.set_alignment(0, 0)
        self.taskNewButton = Gtk.ToolButton(stock_id=Gtk.STOCK_NEW, label="New Task")
        self.taskNewButton.connect("clicked", self.new_task)
        self.taskDelButton = Gtk.ToolButton(stock_id=Gtk.STOCK_DELETE, label="Remove Task")
        self.taskDelButton.set_sensitive(False)
        self.taskDelButton.connect("clicked", self.on_task_del_clicked)
        self.taskTree = Gtk.TreeView(self.create_task_model())
        self.taskTree.set_rules_hint(True)
        self.taskTree.get_selection().connect("changed", self.on_task_selection_changed)
        self.create_task_columns(self.taskTree)

        self.box.pack_start(self.infobox, True, True, 5)
        self.infobox.pack_start(self.infoTitle, False, False, 5)
        self.infobox.pack_start(self.infoscroll, True, True, 2)
        self.infobox.pack_start(self.toolbar, False, False, 0)
        self.infoscroll.add_with_viewport(self.infoinner)
        self.taskBox.pack_start(self.taskLabel, True, True, 5)
        self.taskBox.pack_start(self.taskNewButton, False, False, 0)
        self.taskBox.pack_start(self.taskDelButton, False, False, 0)
        self.infoinner.pack_start(self.taskBox, False, False, 5)
        self.infoinner.pack_start(self.taskTree, False, False, 5)
        #self.infoinner.pack_start(Gtk.HSeparator(), False, False, 5)

    def create_model(self):
        listData = []
        quests = self.get_quests()
        for i in quests:
            listData.append((i[0], i[1], i[2]))
        store = Gtk.ListStore(int, str, str)
        for item in listData:
            store.append(item)
        return store

    def create_columns(self, treeView):
        rendererText = Gtk.CellRendererText()
        column = Gtk.TreeViewColumn("Key", rendererText, text=1)
        column.set_sort_column_id(0)
        treeView.append_column(column)
        rendererText = Gtk.CellRendererText()
        column = Gtk.TreeViewColumn("Name", rendererText, text=2)
        column.set_sort_column_id(1)
        treeView.append_column(column)
        treeView.columns_autosize()

    def on_tree_selection_changed(self, selection):
        model, treeiter = selection.get_selected()
        if treeiter != None:
            self.get_quest_info(model[treeiter][0])

    def create_task_model(self):
        listData = []
        for i in self.tasks:
            if i != []: listData.append(((i[0], i[1])))
        store = Gtk.ListStore(int, str)
        for item in listData:
            store.append(item)
        return store

    def create_task_columns(self, treeView):
        rendererText = Gtk.CellRendererText()
        column = Gtk.TreeViewColumn("#", rendererText, text=0)
        column.set_sort_column_id(0)
        treeView.append_column(column)
        rendererText = Gtk.CellRendererText()
        column = Gtk.TreeViewColumn("Text", rendererText, text=1)
        column.set_sort_column_id(1)
        treeView.append_column(column)

    def on_task_selection_changed(self, selection):
        model, treeiter = selection.get_selected()
        if treeiter != None:
            outp = []
            for i in model[treeiter]:
                outp.append(i)
            self.selectedTask = outp
            self.taskDelButton.set_sensitive(True)

    def on_task_del_clicked(self, button):
        for i in self.tasks:
            if i == self.selectedTask:
                self.tasks.remove(i)
        self.rebuild()

    def new_task(self, button):
        dialog = NewTaskDialog(self)
        response = dialog.run()
        if response == Gtk.ResponseType.OK:
            num = int(dialog.numEntry.get_text())
            text = dialog.textEntry.get_text()
            self.tasks.append([num, text])
        dialog.destroy()
        self.rebuild()

    def get_quest_info(self, lineNo):
        fp = open(questsAddress)
        for i, line in enumerate(fp):
            if (i == lineNo):
                quest = json.loads(line)
                self.key = quest[0]
                self.name = quest[1]
                self.tasks = quest[2]
                self.num = i
                break
        fp.close()
        self.rebuild()

    def get_quests(self):
        fp = open(questsAddress)
        questList = []
        for i, line in enumerate(fp):
            quest = json.loads(line)
            num = i
            questList.append((i, quest[0], quest[1]))
        fp.close()
        return questList

    def new_quest(self, button):
        dialog = NewQuestDialog(self)
        response = dialog.run()
        if response == Gtk.ResponseType.OK:
            name = dialog.nameEntry.get_text()
            key = dialog.keyEntry.get_text()
            fp = open(questsAddress, 'a')
            fp.write(json.dumps([key, name, []]) + '\n')
            fp.close()
            model = self.create_model()
            self.treeView.set_model(model)
        dialog.destroy()

    def rebuild(self):
        self.infoTitle.set_text("<span size='25000'><b>" + self.name + "</b></span>")
        self.infoTitle.set_use_markup(True)
        taskMod = self.create_task_model()
        self.taskTree.set_model(taskMod)
        self.taskDelButton.set_sensitive(False)
        self.show_all()

    def update_file(self, button):
        with open(questsAddress, 'r') as ff:
            data = ff.readlines()
            ff.close()
        data[self.num] = json.dumps([self.key, self.name, self.tasks]) + '\n'
        with open(questsAddress, 'w') as ff:
            ff.writelines(data)
            ff.close()

win = QuestWindow()
win.connect("delete-event", Gtk.main_quit)
win.show_all()
Gtk.main()