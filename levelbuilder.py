#!/usr/bin/python
from gi.repository import Gtk
import json
import os

levelsAddress = "Uplift-android/assets/levels.upl"

class NewLevelDialog(Gtk.Dialog):

    def __init__(self, parent):
        Gtk.Dialog.__init__(self, "New level", parent, 0,
            (Gtk.STOCK_CANCEL, Gtk.ResponseType.CANCEL,
             Gtk.STOCK_OK, Gtk.ResponseType.OK))
        box = self.get_content_area()
        nameBox = Gtk.Box(spacing=2)
        nameLabel = Gtk.Label("Tileset")
        self.nameEntry = Gtk.Entry()
        nameBox.pack_start(nameLabel, False, False, 2)
        nameBox.pack_start(self.nameEntry, True, True, 2)
        box.pack_start(nameBox, False, False, 0)
        self.show_all()

class NewDoorDialog(Gtk.Dialog):

    def __init__(self, parent):
        Gtk.Dialog.__init__(self, "New door", parent, 0,
            (Gtk.STOCK_CANCEL, Gtk.ResponseType.CANCEL,
             Gtk.STOCK_OK, Gtk.ResponseType.OK))
        box = self.get_content_area()
        numBox = Gtk.Box(spacing=2)
        numLabel = Gtk.Label("#")
        self.numEntry = Gtk.Entry()
        XBox = Gtk.Box(spacing=2)
        XLabel = Gtk.Label("X")
        self.XEntry = Gtk.Entry()
        YBox = Gtk.Box(spacing=2)
        YLabel = Gtk.Label("Y")
        self.YEntry = Gtk.Entry()
        numBox.pack_start(numLabel, False, False, 2)
        numBox.pack_start(self.numEntry, True, True, 2)
        XBox.pack_start(XLabel, False, False, 2)
        XBox.pack_start(self.XEntry, True, True, 2)
        YBox.pack_start(YLabel, False, False, 2)
        YBox.pack_start(self.YEntry, True, True, 2)
        box.pack_start(numBox, False, False, 0)
        box.pack_start(XBox, False, False, 0)
        box.pack_start(YBox, False, False, 0)
        self.show_all()

class NewEntDialog(Gtk.Dialog):

    def __init__(self, parent):
        Gtk.Dialog.__init__(self, "New entity", parent, 0,
            (Gtk.STOCK_CANCEL, Gtk.ResponseType.CANCEL,
             Gtk.STOCK_OK, Gtk.ResponseType.OK))
        box = self.get_content_area()
        numBox = Gtk.Box(spacing=2)
        numLabel = Gtk.Label("#")
        self.numEntry = Gtk.Entry()
        nameBox = Gtk.Box(spacing=2)
        nameLabel = Gtk.Label("Type")
        self.nameEntry = Gtk.Entry()
        XBox = Gtk.Box(spacing=2)
        XLabel = Gtk.Label("X")
        self.XEntry = Gtk.Entry()
        YBox = Gtk.Box(spacing=2)
        YLabel = Gtk.Label("Y")
        self.YEntry = Gtk.Entry()
        ABox = Gtk.Box(spacing=2)
        ALabel = Gtk.Label("Animation")
        self.AEntry = Gtk.Entry()
        numBox.pack_start(numLabel, False, False, 2)
        numBox.pack_start(self.numEntry, True, True, 2)
        nameBox.pack_start(nameLabel, False, False, 2)
        nameBox.pack_start(self.nameEntry, True, True, 2)
        XBox.pack_start(XLabel, False, False, 2)
        XBox.pack_start(self.XEntry, True, True, 2)
        YBox.pack_start(YLabel, False, False, 2)
        YBox.pack_start(self.YEntry, True, True, 2)
        ABox.pack_start(ALabel, False, False, 2)
        ABox.pack_start(self.AEntry, True, True, 2)
        box.pack_start(numBox, False, False, 0)
        box.pack_start(nameBox, False, False, 0)
        box.pack_start(XBox, False, False, 0)
        box.pack_start(YBox, False, False, 0)
        box.pack_start(ABox, False, False, 0)
        self.show_all()

class LevelWindow(Gtk.Window):

    tileMap = ""
    num = 0
    doors = []
    entities = []
    selectedDoor = []
    selectedEnt = []

    def __init__(self):
        Gtk.Window.__init__(self, title="Uplift Level Designer")
        self.set_size_request(800, 500)
        self.set_default_icon_from_file("Uplift-android/res/drawable-xhdpi/ic_launcher.png")
        self.box = Gtk.Box(spacing=0)
        self.add(self.box)

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
        self.toolbar = Gtk.Toolbar()
        self.toolbar.set_style(Gtk.ToolbarStyle.BOTH)
        self.saveButton = Gtk.ToolButton(stock_id=Gtk.STOCK_SAVE, label="Save")
        self.saveButton.set_expand(True)
        self.saveButton.connect("clicked", self.save_button_clicked)
        self.toolbar.add(self.saveButton)
        self.tilesButton = Gtk.ToolButton(stock_id=Gtk.STOCK_OPEN, label="Open in Tiled")
        self.tilesButton.set_expand(True)
        self.tilesButton.connect("clicked", self.open_in_tiled)
        self.toolbar.add(self.tilesButton)
        self.newButton = Gtk.ToolButton(stock_id=Gtk.STOCK_NEW, label="New Level")
        self.newButton.set_expand(True)
        self.newButton.connect("clicked", self.new_level)
        self.toolbar.add(self.newButton)

        #Doors
        self.doorBox = Gtk.Box(spacing=5)
        self.doorLabel = Gtk.Label("<b>Doors</b>")
        self.doorLabel.set_use_markup(True)
        self.doorLabel.set_alignment(0, 0)
        self.doorNewButton = Gtk.ToolButton(stock_id=Gtk.STOCK_NEW, label="New Door")
        self.doorNewButton.connect("clicked", self.new_door)
        self.doorDelButton = Gtk.ToolButton(stock_id=Gtk.STOCK_DELETE, label="Remove Door")
        self.doorDelButton.set_sensitive(False)
        self.doorDelButton.connect("clicked", self.on_door_del_clicked)
        self.doorTree = Gtk.TreeView(self.create_door_model())
        self.doorTree.set_rules_hint(True)
        self.doorTree.get_selection().connect("changed", self.on_door_selection_changed)
        self.create_door_columns(self.doorTree)

        #Entities
        self.entBox = Gtk.Box(spacing=5)
        self.entLabel = Gtk.Label("<b>Entities</b>")
        self.entLabel.set_use_markup(True)
        self.entLabel.set_alignment(0, 0)
        self.entNewButton = Gtk.ToolButton(stock_id=Gtk.STOCK_NEW, label="New Entity")
        self.entNewButton.connect("clicked", self.new_ent)
        self.entDelButton = Gtk.ToolButton(stock_id=Gtk.STOCK_DELETE, label="Remove Entity")
        self.entDelButton.set_sensitive(False)
        self.entDelButton.connect("clicked", self.on_ent_del_clicked)
        self.entTree = Gtk.TreeView(self.create_ent_model())
        self.entTree.set_rules_hint(True)
        self.entTree.get_selection().connect("changed", self.on_ent_selection_changed)
        self.create_ent_columns(self.entTree)

        self.box.pack_start(self.infobox, True, True, 5)
        self.infobox.pack_start(self.infoTitle, False, False, 5)
        self.infobox.pack_start(self.infoscroll, True, True, 2)
        self.infobox.pack_start(self.toolbar, False, False, 0)
        self.infoscroll.add_with_viewport(self.infoinner)
        self.doorBox.pack_start(self.doorLabel, True, True, 5)
        self.doorBox.pack_start(self.doorNewButton, False, False, 0)
        self.doorBox.pack_start(self.doorDelButton, False, False, 0)
        self.infoinner.pack_start(self.doorBox, False, False, 5)
        self.infoinner.pack_start(self.doorTree, False, False, 5)
        self.infoinner.pack_start(Gtk.HSeparator(), False, False, 5)
        self.entBox.pack_start(self.entLabel, True, True, 5)
        self.entBox.pack_start(self.entNewButton, False, False, 0)
        self.entBox.pack_start(self.entDelButton, False, False, 0)
        self.infoinner.pack_start(self.entBox, False, False, 5)
        self.infoinner.pack_start(self.entTree, False, False, 5)

    def create_model(self):
        listData = []
        levels = self.get_levels()
        for i in levels:
            listData.append(((i[0]), i[1]))
        store = Gtk.ListStore(int, str)
        for item in listData:
            store.append(item)
        return store

    def create_columns(self, treeView):
        rendererText = Gtk.CellRendererText()
        column = Gtk.TreeViewColumn("#", rendererText, text=0)
        column.set_sort_column_id(0)
        treeView.append_column(column)
        rendererText = Gtk.CellRendererText()
        column = Gtk.TreeViewColumn("Name", rendererText, text=1)
        column.set_sort_column_id(1)
        treeView.append_column(column)
        treeView.columns_autosize()

    def create_door_model(self):
        listData = []
        for i in self.doors:
            if i != []: listData.append(((i[0], i[1], i[2])))
        store = Gtk.ListStore(int, int, int)
        for item in listData:
            store.append(item)
        return store

    def create_door_columns(self, treeView):
        rendererText = Gtk.CellRendererText()
        column = Gtk.TreeViewColumn("#", rendererText, text=0)
        column.set_sort_column_id(0)
        treeView.append_column(column)
        rendererText = Gtk.CellRendererText()
        column = Gtk.TreeViewColumn("X", rendererText, text=1)
        column.set_sort_column_id(1)
        treeView.append_column(column)
        rendererText = Gtk.CellRendererText()
        column = Gtk.TreeViewColumn("Y", rendererText, text=2)
        column.set_sort_column_id(2)
        treeView.append_column(column)

    def create_ent_model(self):
        listData = []
        for i in self.entities:
            if i != []: listData.append(((i[0], i[1], i[2], i[3], i[4])))
        store = Gtk.ListStore(int, str, int, int, int)
        for item in listData:
            store.append(item)
        return store

    def create_ent_columns(self, treeView):
        rendererText = Gtk.CellRendererText()
        column = Gtk.TreeViewColumn("id", rendererText, text=0)
        column.set_sort_column_id(0)
        treeView.append_column(column)
        rendererText = Gtk.CellRendererText()
        column = Gtk.TreeViewColumn("type", rendererText, text=1)
        column.set_sort_column_id(1)
        treeView.append_column(column)
        rendererText = Gtk.CellRendererText()
        column = Gtk.TreeViewColumn("X", rendererText, text=2)
        column.set_sort_column_id(2)
        treeView.append_column(column)
        column = Gtk.TreeViewColumn("Y", rendererText, text=3)
        column.set_sort_column_id(3)
        treeView.append_column(column)
        column = Gtk.TreeViewColumn("Animation", rendererText, text=4)
        column.set_sort_column_id(4)
        treeView.append_column(column)

    def on_tree_selection_changed(self, selection):
        model, treeiter = selection.get_selected()
        if treeiter != None:
            self.get_level_info(model[treeiter][0])

    def on_door_selection_changed(self, selection):
        model, treeiter = selection.get_selected()
        if treeiter != None:
            outp = []
            for i in model[treeiter]:
                outp.append(i)
            self.selectedDoor = outp
            self.doorDelButton.set_sensitive(True)

    def on_door_del_clicked(self, button):
        self.delete_door(self.selectedDoor)

    def on_ent_selection_changed(self, selection):
        model, treeiter = selection.get_selected()
        if treeiter != None:
            outp = []
            for i in model[treeiter]:
                outp.append(i)
            self.selectedEnt = outp
            self.entDelButton.set_sensitive(True)

    def on_ent_del_clicked(self, button):
        self.delete_ent(self.selectedEnt)

    def get_level_info(self, lineNo):
        fp = open(levelsAddress)
        for i, line in enumerate(fp):
            if (i == lineNo):
                level = json.loads(line)
                self.num = i
                self.tileMap = level[0]
                self.doors = level[1]
                self.entities = level[2]
                break
        fp.close()
        self.rebuild()

    def delete_door(self, door):
        for i in self.doors:
            if i == door:
                self.doors.remove(i)
        self.rebuild()

    def new_door(self, button):
        dialog = NewDoorDialog(self)
        response = dialog.run()
        if response == Gtk.ResponseType.OK:
            num = int(dialog.numEntry.get_text())
            X = int(dialog.XEntry.get_text())
            Y = int(dialog.YEntry.get_text())
            self.doors.append([num, X, Y])
        dialog.destroy()
        self.rebuild()

    def new_ent(self, button):
        dialog = NewEntDialog(self)
        response = dialog.run()
        if response == Gtk.ResponseType.OK:
            num = int(dialog.numEntry.get_text())
            etype = dialog.nameEntry.get_text()
            X = int(dialog.XEntry.get_text())
            Y = int(dialog.YEntry.get_text())
            A = int(dialog.AEntry.get_text())
            self.entities.append([num, etype, X, Y, A])
        dialog.destroy()
        self.rebuild()

    def delete_ent(self, ent):
        for i in self.entities:
            if i == ent:
                self.entities.remove(i)
        self.rebuild()

    def rebuild(self):
        self.infoTitle.set_text("<span size='25000'><b>tmx/" + self.tileMap + ".tmx</b></span>")
        self.infoTitle.set_use_markup(True)
        doorMod = self.create_door_model()
        self.doorTree.set_model(doorMod)
        entMod = self.create_ent_model()
        self.entTree.set_model(entMod)
        self.entDelButton.set_sensitive(False)
        self.doorDelButton.set_sensitive(False)
        self.show_all()

    def open_in_tiled(self, button):
        path = "tiled Uplift-android/assets/tmx/" + self.tileMap + ".tmx &"
        os.system(path)

    def save_button_clicked(self, button):
        self.update_file(self.num)

    def new_level(self, button):
        dialog = NewLevelDialog(self)
        response = dialog.run()
        if response == Gtk.ResponseType.OK:
            name = dialog.nameEntry.get_text()
            fp = open(levelsAddress, 'a')
            fp.write(json.dumps([name, [], []]) + '\n')
            fp.close()
            model = self.create_model()
            self.treeView.set_model(model)
        dialog.destroy()

    def get_levels(self):
        fp = open(levelsAddress)
        levelList = []
        for i, line in enumerate(fp):
            level = json.loads(line)
            levelList.append((i, level[0]))
        fp.close()
        return levelList

    def update_file(self, lineNo):
        with open(levelsAddress, 'r') as ff:
            data = ff.readlines()
            ff.close()
        data[lineNo] = json.dumps([self.tileMap, self.doors, self.entities]) + '\n'
        with open(levelsAddress, 'w') as ff:
            ff.writelines(data)
            ff.close()

win = LevelWindow()
win.connect("delete-event", Gtk.main_quit)
win.show_all()
Gtk.main()