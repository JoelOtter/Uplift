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
        soundBox = Gtk.Box(spacing=2)
        soundLabel = Gtk.Label("Music")
        self.soundEntry = Gtk.Entry()
        nameBox.pack_start(nameLabel, False, False, 2)
        nameBox.pack_start(self.soundEntry, True, True, 2)
        soundBox.pack_start(soundLabel, False, False, 2)
        soundBox.pack_start(self.soundEntry, True, True, 2)
        box.pack_start(nameBox, False, False, 0)
        box.pack_start(soundBox, False, False, 0)
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
        toBox = Gtk.Box(spacing=2)
        toLabel = Gtk.Label("To tileset #")
        self.toEntry = Gtk.Entry()
        toNBox = Gtk.Box(spacing=2)
        toNLabel = Gtk.Label("To door #")
        self.toNEntry = Gtk.Entry()
        numBox.pack_start(numLabel, False, False, 2)
        numBox.pack_start(self.numEntry, True, True, 2)
        XBox.pack_start(XLabel, False, False, 2)
        XBox.pack_start(self.XEntry, True, True, 2)
        YBox.pack_start(YLabel, False, False, 2)
        YBox.pack_start(self.YEntry, True, True, 2)
        toBox.pack_start(toLabel, False, False, 2)
        toBox.pack_start(self.toEntry, True, True, 2)
        toNBox.pack_start(toNLabel, False, False, 2)
        toNBox.pack_start(self.toNEntry, True, True, 2)
        box.pack_start(numBox, False, False, 0)
        box.pack_start(XBox, False, False, 0)
        box.pack_start(YBox, False, False, 0)
        box.pack_start(toBox, False, False, 0)
        box.pack_start(toNBox, False, False, 0)
        self.show_all()

class NewEntDialog(Gtk.Dialog):

    def __init__(self, parent):
        Gtk.Dialog.__init__(self, "New NPC", parent, 0,
            (Gtk.STOCK_CANCEL, Gtk.ResponseType.CANCEL,
             Gtk.STOCK_OK, Gtk.ResponseType.OK))
        box = self.get_content_area()
        tilesBox = Gtk.Box(spacing=2)
        tilesLabel = Gtk.Label("Tileset")
        self.tilesEntry = Gtk.Entry()
        XBox = Gtk.Box(spacing=2)
        XLabel = Gtk.Label("X")
        self.XEntry = Gtk.Entry()
        YBox = Gtk.Box(spacing=2)
        YLabel = Gtk.Label("Y")
        self.YEntry = Gtk.Entry()
        ABox = Gtk.Box(spacing=2)
        ALabel = Gtk.Label("Animation")
        self.AEntry = Gtk.Entry()
        DBox = Gtk.Box(spacing=2)
        DLabel = Gtk.Label("Direction")
        self.DEntry = Gtk.Entry()
        SBox = Gtk.Box(spacing=2)
        SLabel = Gtk.Label("Speed")
        self.SEntry = Gtk.Entry()
        QBox = Gtk.Box(spacing=2)
        QLabel = Gtk.Label("Quest")
        self.QEntry = Gtk.Entry()
        tilesBox.pack_start(tilesLabel, False, False, 2)
        tilesBox.pack_start(self.tilesEntry, True, True, 2)
        XBox.pack_start(XLabel, False, False, 2)
        XBox.pack_start(self.XEntry, True, True, 2)
        YBox.pack_start(YLabel, False, False, 2)
        YBox.pack_start(self.YEntry, True, True, 2)
        ABox.pack_start(ALabel, False, False, 2)
        ABox.pack_start(self.AEntry, True, True, 2)
        DBox.pack_start(DLabel, False, False, 2)
        DBox.pack_start(self.DEntry, True, True, 2)
        SBox.pack_start(SLabel, False, False, 2)
        SBox.pack_start(self.SEntry, True, True, 2)
        QBox.pack_start(QLabel, False, False, 2)
        QBox.pack_start(self.QEntry, True, True, 2)
        box.pack_start(tilesBox, False, False, 0)
        box.pack_start(XBox, False, False, 0)
        box.pack_start(YBox, False, False, 0)
        box.pack_start(ABox, False, False, 0)
        box.pack_start(DBox, False, False, 0)
        box.pack_start(SBox, False, False, 0)
        box.pack_start(QBox, False, False, 0)
        self.show_all()

class NewEnemyDialog(Gtk.Dialog):

    def __init__(self, parent):
        Gtk.Dialog.__init__(self, "New NPC", parent, 0,
            (Gtk.STOCK_CANCEL, Gtk.ResponseType.CANCEL,
             Gtk.STOCK_OK, Gtk.ResponseType.OK))
        box = self.get_content_area()
        typeBox = Gtk.Box(spacing=2)
        typeLabel = Gtk.Label("Type")
        self.typeEntry = Gtk.Entry()
        tilesBox = Gtk.Box(spacing=2)
        tilesLabel = Gtk.Label("Tileset")
        self.tilesEntry = Gtk.Entry()
        XBox = Gtk.Box(spacing=2)
        XLabel = Gtk.Label("X")
        self.XEntry = Gtk.Entry()
        YBox = Gtk.Box(spacing=2)
        YLabel = Gtk.Label("Y")
        self.YEntry = Gtk.Entry()
        HPBox = Gtk.Box(spacing=2)
        HPLabel = Gtk.Label("HP")
        self.HPEntry = Gtk.Entry()
        typeBox.pack_start(typeLabel, False, False, 2)
        typeBox.pack_start(self.typeEntry, True, True, 2)
        tilesBox.pack_start(tilesLabel, False, False, 2)
        tilesBox.pack_start(self.tilesEntry, True, True, 2)
        XBox.pack_start(XLabel, False, False, 2)
        XBox.pack_start(self.XEntry, True, True, 2)
        YBox.pack_start(YLabel, False, False, 2)
        YBox.pack_start(self.YEntry, True, True, 2)
        HPBox.pack_start(HPLabel, False, False, 2)
        HPBox.pack_start(self.HPEntry, True, True, 2)
        box.pack_start(typeBox, False, False, 0)
        box.pack_start(tilesBox, False, False, 0)
        box.pack_start(XBox, False, False, 0)
        box.pack_start(YBox, False, False, 0)
        box.pack_start(HPBox, False, False, 0)
        self.show_all()

class NewInteractableDialog(Gtk.Dialog):

    def __init__(self, parent):
        Gtk.Dialog.__init__(self, "New Interactable", parent, 0,
            (Gtk.STOCK_CANCEL, Gtk.ResponseType.CANCEL,
             Gtk.STOCK_OK, Gtk.ResponseType.OK))
        box = self.get_content_area()
        typeBox = Gtk.Box(spacing=2)
        typeLabel = Gtk.Label("Type")
        self.typeEntry = Gtk.Entry()
        XBox = Gtk.Box(spacing=2)
        XLabel = Gtk.Label("X")
        self.XEntry = Gtk.Entry()
        YBox = Gtk.Box(spacing=2)
        YLabel = Gtk.Label("Y")
        self.YEntry = Gtk.Entry()
        conBox = Gtk.Box(spacing=2)
        conLabel = Gtk.Label("Contents")
        self.conEntry = Gtk.Entry()
        typeBox.pack_start(typeLabel, False, False, 2)
        typeBox.pack_start(self.typeEntry, True, True, 2)
        XBox.pack_start(XLabel, False, False, 2)
        XBox.pack_start(self.XEntry, True, True, 2)
        YBox.pack_start(YLabel, False, False, 2)
        YBox.pack_start(self.YEntry, True, True, 2)
        conBox.pack_start(conLabel, False, False, 2)
        conBox.pack_start(self.conEntry, True, True, 2)
        box.pack_start(typeBox, False, False, 0)
        box.pack_start(XBox, False, False, 0)
        box.pack_start(YBox, False, False, 0)
        box.pack_start(conBox, False, False, 0)
        self.show_all()

class ConversationDialog(Gtk.Dialog):

    convs = None
    selectedConv = None

    def __init__(self, parent):
        self.convs = list(parent.selectedEnt[6][1])
        Gtk.Dialog.__init__(self, "Conversation Viewer", parent, 0,
            (Gtk.STOCK_CANCEL, Gtk.ResponseType.CANCEL,
             Gtk.STOCK_OK, Gtk.ResponseType.OK))
        self.set_size_request(700, 400)
        self.box = self.get_content_area()
        self.tree = Gtk.TreeView(self.create_model())
        self.tree.set_rules_hint(True)
        self.tree.get_selection().connect("changed", self.on_tree_selection_changed)
        self.create_columns(self.tree)
        self.toolbar = Gtk.Toolbar()
        self.toolbar.set_style(Gtk.ToolbarStyle.BOTH_HORIZ)
        self.newbutton = Gtk.ToolButton(stock_id=Gtk.STOCK_NEW)
        self.deletebutton = Gtk.ToolButton(stock_id=Gtk.STOCK_DELETE)
        self.deletebutton.connect("clicked", self.delete_conv)
        self.newbutton.set_expand(True)
        self.newbutton.connect("clicked", self.new_conv)
        self.deletebutton.set_expand(True)
        self.deletebutton.set_sensitive(False)
        self.toolbar.add(self.newbutton)
        self.toolbar.add(self.deletebutton)
        self.box.pack_start(self.tree, True, True, 5)
        self.box.pack_start(self.toolbar, False, False, 5)
        self.show_all()

    def create_model(self):
        listData = []
        for i in self.convs:
            if i != []: listData.append(((i[0], i[1], i[2])))
        store = Gtk.ListStore(int, str, str)
        for item in listData:
            store.append(item)
        return store

    def create_columns(self, treeView):
        rendererText = Gtk.CellRendererText()
        column = Gtk.TreeViewColumn(">=", rendererText, text=0)
        column.set_sort_column_id(0)
        treeView.append_column(column)
        column = Gtk.TreeViewColumn("Post", rendererText, text=1)
        column.set_sort_column_id(1)
        treeView.append_column(column)
        column = Gtk.TreeViewColumn("Text", rendererText, text=2)
        column.set_sort_column_id(2)
        treeView.append_column(column)
        treeView.columns_autosize()

    def on_tree_selection_changed(self, selection):
        model, treeiter = selection.get_selected()
        if treeiter != None:
            outp = []
            for i in model[treeiter]:
                outp.append(i)
            self.selectedConv = outp
            self.deletebutton.set_sensitive(True)

    def delete_conv(self, widget):
        self.convs.remove(self.selectedConv)
        newmod = self.create_model()
        self.tree.set_model(newmod)
        self.deletebutton.set_sensitive(False)
        self.show_all()

    def new_conv(self, widget):
        dialog = NewConvDialog(self)
        response = dialog.run()
        if response == Gtk.ResponseType.OK:
            viewbuf = dialog.textEntry.get_buffer()
            text = viewbuf.get_text(viewbuf.get_start_iter(), viewbuf.get_end_iter(), include_hidden_chars=True)
            self.convs.append([int(dialog.numEntry.get_text()), dialog.postEntry.get_text(), text])
        dialog.destroy()
        newmod = self.create_model()
        self.tree.set_model(newmod)
        self.show_all()

class NewConvDialog(Gtk.Dialog):

    def __init__(self, parent):
        Gtk.Dialog.__init__(self, "New conversation option", parent, 0,
            (Gtk.STOCK_CANCEL, Gtk.ResponseType.CANCEL,
             Gtk.STOCK_OK, Gtk.ResponseType.OK))
        box = self.get_content_area()
        self.set_size_request(400, 300)
        numLabel = Gtk.Label(">=")
        self.numEntry = Gtk.Entry()
        postLabel = Gtk.Label("Post")
        self.postEntry = Gtk.Entry()
        numBox = Gtk.Box(spacing=5)
        numBox.pack_start(numLabel, False, False, 5)
        numBox.pack_start(self.numEntry, True, True, 5)
        numBox.pack_start(postLabel, False, False, 5)
        numBox.pack_start(self.postEntry, True, True, 5)
        box.pack_start(numBox, False, False, 5)
        scrolledwindow = Gtk.ScrolledWindow()
        scrolledwindow.set_hexpand(True)
        scrolledwindow.set_vexpand(True)
        self.textEntry = Gtk.TextView()
        self.textEntry.set_wrap_mode(Gtk.WrapMode.WORD_CHAR)
        box.pack_start(scrolledwindow, True, True, 5)
        scrolledwindow.add_with_viewport(self.textEntry)
        self.show_all()

class LevelWindow(Gtk.Window):

    tileMap = ""
    sound = ""
    num = 0
    doors = []
    entities = []
    enemies = []
    interactables = []
    selectedDoor = []
    selectedEnt = []
    selectedEnemy = []
    selectedInteractable = []

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
        self.soundTitle = Gtk.Label("Music")
        self.soundTitle.set_alignment(0, 0)
        self.toolbar = Gtk.Toolbar()
        self.toolbar.set_style(Gtk.ToolbarStyle.BOTH_HORIZ)
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

        #NPCs
        self.entBox = Gtk.Box(spacing=5)
        self.entLabel = Gtk.Label("<b>NPCs</b>")
        self.entLabel.set_use_markup(True)
        self.entLabel.set_alignment(0, 0)
        self.entNewButton = Gtk.ToolButton(stock_id=Gtk.STOCK_NEW, label="New NPC")
        self.entNewButton.connect("clicked", self.new_ent)
        self.entDelButton = Gtk.ToolButton(stock_id=Gtk.STOCK_DELETE, label="Remove NPC")
        self.entDelButton.set_sensitive(False)
        self.entDelButton.connect("clicked", self.on_ent_del_clicked)
        self.entTree = Gtk.TreeView(self.create_ent_model())
        self.entTree.set_rules_hint(True)
        self.entTree.get_selection().connect("changed", self.on_ent_selection_changed)
        self.entTree.connect("row-activated", self.npc_row_activated)
        self.create_ent_columns(self.entTree)

        #Enemies
        self.enemyBox = Gtk.Box(spacing=5)
        self.enemyLabel = Gtk.Label("<b>Enemies</b>")
        self.enemyLabel.set_use_markup(True)
        self.enemyLabel.set_alignment(0, 0)
        self.enemyNewButton = Gtk.ToolButton(stock_id=Gtk.STOCK_NEW, label="New Enemy")
        self.enemyNewButton.connect("clicked", self.new_enemy)
        self.enemyDelButton = Gtk.ToolButton(stock_id=Gtk.STOCK_DELETE, label="Remove Enemy")
        self.enemyDelButton.set_sensitive(False)
        self.enemyDelButton.connect("clicked", self.on_enemy_del_clicked)
        self.enemyTree = Gtk.TreeView(self.create_enemy_model())
        self.enemyTree.set_rules_hint(True)
        self.enemyTree.get_selection().connect("changed", self.on_enemy_selection_changed)
        self.enemyTree.connect("row-activated", self.enemy_row_activated)
        self.create_enemy_columns(self.enemyTree)

        #Interactables
        self.interactableBox = Gtk.Box(spacing=5)
        self.interactableLabel = Gtk.Label("<b>Interactables</b>")
        self.interactableLabel.set_use_markup(True)
        self.interactableLabel.set_alignment(0, 0)
        self.interactableNewButton = Gtk.ToolButton(stock_id=Gtk.STOCK_NEW, label="New Interactable")
        self.interactableNewButton.connect("clicked", self.new_interactable)
        self.interactableDelButton = Gtk.ToolButton(stock_id=Gtk.STOCK_DELETE, label="Remove Interactable")
        self.interactableDelButton.set_sensitive(False)
        self.interactableDelButton.connect("clicked", self.on_interactable_del_clicked)
        self.interactableTree = Gtk.TreeView(self.create_interactable_model())
        self.interactableTree.set_rules_hint(True)
        self.interactableTree.get_selection().connect("changed", self.on_interactable_selection_changed)
        self.create_interactable_columns(self.interactableTree)

        self.box.pack_start(self.infobox, True, True, 5)
        self.infobox.pack_start(self.infoTitle, False, False, 5)
        self.infobox.pack_start(self.soundTitle, False, False, 5)
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
        self.infoinner.pack_start(Gtk.HSeparator(), False, False, 5)
        self.enemyBox.pack_start(self.enemyLabel, True, True, 5)
        self.enemyBox.pack_start(self.enemyNewButton, False, False, 0)
        self.enemyBox.pack_start(self.enemyDelButton, False, False, 0)
        self.infoinner.pack_start(self.enemyBox, False, False, 5)
        self.infoinner.pack_start(self.enemyTree, False, False, 5)
        self.infoinner.pack_start(Gtk.HSeparator(), False, False, 5)
        self.interactableBox.pack_start(self.interactableLabel, True, True, 5)
        self.interactableBox.pack_start(self.interactableNewButton, False, False, 0)
        self.interactableBox.pack_start(self.interactableDelButton, False, False, 0)
        self.infoinner.pack_start(self.interactableBox, False, False, 5)
        self.infoinner.pack_start(self.interactableTree, False, False, 5)

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
            if i != []: listData.append(((i[0], i[1], i[2], i[3], i[4])))
        store = Gtk.ListStore(int, int, int, int, int)
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
        rendererText = Gtk.CellRendererText()
        column = Gtk.TreeViewColumn("To tileset #", rendererText, text=3)
        column.set_sort_column_id(3)
        treeView.append_column(column)
        rendererText = Gtk.CellRendererText()
        column = Gtk.TreeViewColumn("To door #", rendererText, text=4)
        column.set_sort_column_id(4)
        treeView.append_column(column)

    def create_ent_model(self):
        listData = []
        for i in self.entities:
            if i != []: listData.append(((i[0], i[1], i[2], i[3], i[4], i[5], i[6][0])))
        store = Gtk.ListStore(str, int, int, int, int, float, str)
        for item in listData:
            store.append(item)
        return store

    def create_ent_columns(self, treeView):
        rendererText = Gtk.CellRendererText()
        column = Gtk.TreeViewColumn("tiles", rendererText, text=0)
        column.set_sort_column_id(0)
        treeView.append_column(column)
        rendererText = Gtk.CellRendererText()
        column = Gtk.TreeViewColumn("X", rendererText, text=1)
        column.set_sort_column_id(1)
        treeView.append_column(column)
        column = Gtk.TreeViewColumn("Y", rendererText, text=2)
        column.set_sort_column_id(2)
        treeView.append_column(column)
        column = Gtk.TreeViewColumn("Animation", rendererText, text=3)
        column.set_sort_column_id(3)
        treeView.append_column(column)
        column = Gtk.TreeViewColumn("Direction", rendererText, text=4)
        column.set_sort_column_id(4)
        treeView.append_column(column)
        column = Gtk.TreeViewColumn("Speed", rendererText, text=5)
        column.set_sort_column_id(5)
        treeView.append_column(column)
        column = Gtk.TreeViewColumn("Quest", rendererText, text=6)
        column.set_sort_column_id(6)
        treeView.append_column(column)

    def create_enemy_model(self):
        listData = []
        for i in self.enemies:
            if i != []: listData.append(((i[0], i[1], i[2], i[3], i[4])))
        store = Gtk.ListStore(str, str, int, int, int)
        for item in listData:
            store.append(item)
        return store

    def create_enemy_columns(self, treeView):
        rendererText = Gtk.CellRendererText()
        column = Gtk.TreeViewColumn("Type", rendererText, text=0)
        column.set_sort_column_id(0)
        treeView.append_column(column)
        column = Gtk.TreeViewColumn("Tiles", rendererText, text=1)
        column.set_sort_column_id(1)
        treeView.append_column(column)
        column = Gtk.TreeViewColumn("X", rendererText, text=2)
        column.set_sort_column_id(2)
        treeView.append_column(column)
        column = Gtk.TreeViewColumn("Y", rendererText, text=3)
        column.set_sort_column_id(3)
        treeView.append_column(column)
        column = Gtk.TreeViewColumn("HP", rendererText, text=4)
        column.set_sort_column_id(4)
        treeView.append_column(column)

    def create_interactable_model(self):
        listData = []
        for i in self.interactables:
            if i != []: listData.append(((i[0], i[1], i[2], i[3])))
        store = Gtk.ListStore(str, int, int, int)
        for item in listData:
            store.append(item)
        return store

    def create_interactable_columns(self, treeView):
        rendererText = Gtk.CellRendererText()
        column = Gtk.TreeViewColumn("Type", rendererText, text=0)
        column.set_sort_column_id(0)
        treeView.append_column(column)
        column = Gtk.TreeViewColumn("X", rendererText, text=1)
        column.set_sort_column_id(1)
        treeView.append_column(column)
        column = Gtk.TreeViewColumn("Y", rendererText, text=2)
        column.set_sort_column_id(2)
        treeView.append_column(column)
        column = Gtk.TreeViewColumn("Contents", rendererText, text=3)
        column.set_sort_column_id(3)
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
            self.selectedEnt = self.get_relevant_ent(outp)
            self.entDelButton.set_sensitive(True)

    def on_ent_del_clicked(self, button):
        self.delete_ent(self.selectedEnt)

    def on_enemy_selection_changed(self, selection):
        model, treeiter = selection.get_selected()
        if treeiter != None:
            outp = []
            for i in model[treeiter]:
                outp.append(i)
            self.selectedEnemy = outp
            self.enemyDelButton.set_sensitive(True)

    def on_interactable_selection_changed(self, selection):
        model, treeiter = selection.get_selected()
        if treeiter != None:
            outp = []
            for i in model[treeiter]:
                outp.append(i)
            self.selectedInteractable = outp
            self.interactableDelButton.set_sensitive(True)

    def npc_row_activated(treeview, iterr, path, data):
        dialog = ConversationDialog(treeview)
        response = dialog.run()
        if response == Gtk.ResponseType.OK:
            treeview.selectedEnt[6][1] = list(dialog.convs)
        dialog.destroy()

    def enemy_row_activated(treeview, iterr, path, data):
        os.system("eog Uplift-android/assets/gfx/" + treeview.selectedEnemy[1] + " &")

    def on_enemy_del_clicked(self, button):
        self.delete_enemy(self.selectedEnemy)

    def on_interactable_del_clicked(self, button):
        self.delete_interactable(self.selectedInteractable)

    def get_level_info(self, lineNo):
        fp = open(levelsAddress)
        for i, line in enumerate(fp):
            if (i == lineNo):
                level = json.loads(line)
                self.num = i
                self.tileMap = level[0]
                self.sound = level[1]
                self.doors = level[2]
                self.entities = level[3]
                self.enemies = level[4]
                self.interactables = level[5]
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
            to = int(dialog.toEntry.get_text())
            toN = int(dialog.toNEntry.get_text())
            self.doors.append([num, X, Y, to, toN])
        dialog.destroy()
        self.rebuild()

    def new_ent(self, button):
        dialog = NewEntDialog(self)
        response = dialog.run()
        if response == Gtk.ResponseType.OK:
            tiles = dialog.tilesEntry.get_text()
            X = int(dialog.XEntry.get_text())
            Y = int(dialog.YEntry.get_text())
            A = int(dialog.AEntry.get_text())
            D = int(dialog.DEntry.get_text())
            S = float(dialog.SEntry.get_text())
            Q = dialog.QEntry.get_text()
            self.entities.append([tiles, X, Y, A, D, S, [Q,[]]])
        dialog.destroy()
        self.rebuild()

    def new_enemy(self, button):
        dialog = NewEnemyDialog(self)
        response = dialog.run()
        if response == Gtk.ResponseType.OK:
            etype = dialog.typeEntry.get_text()
            tiles = dialog.tilesEntry.get_text()
            X = int(dialog.XEntry.get_text())
            Y = int(dialog.YEntry.get_text())
            HP = int(dialog.HPEntry.get_text())
            self.enemies.append([etype, tiles, X, Y, HP])
        dialog.destroy()
        self.rebuild()

    def new_interactable(self, button):
        dialog = NewInteractableDialog(self)
        response = dialog.run()
        if response == Gtk.ResponseType.OK:
            etype = dialog.typeEntry.get_text()
            X = int(dialog.XEntry.get_text())
            Y = int(dialog.YEntry.get_text())
            con = int(dialog.conEntry.get_text())
            self.interactables.append([etype, X, Y, con])
        dialog.destroy()
        self.rebuild()

    def delete_ent(self, ent):
        for i in self.entities:
            if (i == ent):
                self.entities.remove(i)
        self.rebuild()

    def delete_enemy(self, enemy):
        for i in self.enemies:
            if i == enemy:
                self.enemies.remove(i)
        self.rebuild()

    def delete_interactable(self, interactable):
        for i in self.interactables:
            if i == interactable:
                self.interactables.remove(i)
        self.rebuild()

    def rebuild(self):
        self.infoTitle.set_text("<span size='25000'><b>tmx/" + self.tileMap + ".tmx</b></span>")
        self.infoTitle.set_use_markup(True)
        self.soundTitle.set_text("<span size='10000'><b>Music: </b>" + self.sound + "</span>")
        self.soundTitle.set_use_markup(True)
        doorMod = self.create_door_model()
        self.doorTree.set_model(doorMod)
        entMod = self.create_ent_model()
        self.entTree.set_model(entMod)
        enemyMod = self.create_enemy_model()
        self.enemyTree.set_model(enemyMod)
        interactableMod = self.create_interactable_model()
        self.interactableTree.set_model(interactableMod)
        self.entDelButton.set_sensitive(False)
        self.doorDelButton.set_sensitive(False)
        self.enemyDelButton.set_sensitive(False)
        self.interactableDelButton.set_sensitive(False)
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
            name = dialog.soundEntry.get_text()
            fp = open(levelsAddress, 'a')
            fp.write(json.dumps([name, sound, [], []]) + '\n')
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
        data[lineNo] = json.dumps([self.tileMap, self.sound, self.doors, self.entities, self.enemies, self.interactables]) + '\n'
        with open(levelsAddress, 'w') as ff:
            ff.writelines(data)
            ff.close()

    def get_relevant_ent(self, entity):
        for i in self.entities:
            if (i[0] == entity[0]) & (i[1] == entity[1]) & (i[2] == entity[2]):
                return i
        return None

win = LevelWindow()
win.connect("delete-event", Gtk.main_quit)
win.show_all()
Gtk.main()