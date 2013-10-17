#!/usr/bin/python
from gi.repository import Gtk
import json
import os

levelsAddress = "Uplift-android/assets/space.upl"

class NewStarDialog(Gtk.Dialog):

    def __init__(self, parent):
        Gtk.Dialog.__init__(self, "New star", parent, 0,
            (Gtk.STOCK_CANCEL, Gtk.ResponseType.CANCEL,
             Gtk.STOCK_OK, Gtk.ResponseType.OK))
        box = self.get_content_area()
        spriteBox = Gtk.Box(spacing=2)
        spriteLabel = Gtk.Label("Sprite")
        self.spriteEntry = Gtk.Entry()
        descBox = Gtk.Box(spacing=2)
        descLabel = Gtk.Label("Description")
        self.descEntry = Gtk.Entry()
        XBox = Gtk.Box(spacing=2)
        XLabel = Gtk.Label("X")
        self.XEntry = Gtk.Entry()
        YBox = Gtk.Box(spacing=2)
        YLabel = Gtk.Label("Y")
        self.YEntry = Gtk.Entry()
        sizeBox = Gtk.Box(spacing=2)
        sizeLabel = Gtk.Label("Size")
        self.sizeEntry = Gtk.Entry()
        spinBox = Gtk.Box(spacing=2)
        spinLabel = Gtk.Label("Rotation speed")
        self.spinEntry = Gtk.Entry()
        spriteBox.pack_start(spriteLabel, False, False, 2)
        spriteBox.pack_start(self.spriteEntry, True, True, 2)
        descBox.pack_start(descLabel, False, False, 2)
        descBox.pack_start(self.descEntry, True, True, 2)
        XBox.pack_start(XLabel, False, False, 2)
        XBox.pack_start(self.XEntry, True, True, 2)
        YBox.pack_start(YLabel, False, False, 2)
        YBox.pack_start(self.YEntry, True, True, 2)
        sizeBox.pack_start(sizeLabel, False, False, 2)
        sizeBox.pack_start(self.sizeEntry, True, True, 2)
        spinBox.pack_start(spinLabel, False, False, 2)
        spinBox.pack_start(self.spinEntry, True, True, 2)
        box.pack_start(spriteBox, False, False, 0)
        box.pack_start(descBox, False, False, 0)
        box.pack_start(XBox, False, False, 0)
        box.pack_start(YBox, False, False, 0)
        box.pack_start(sizeBox, False, False, 0)
        box.pack_start(spinBox, False, False, 0)
        self.show_all()

class SpaceWindow(Gtk.Window):

    name = ""
    sound = ""
    num = 0
    stars = []
    planets = []
    warps = []
    selectedStar = []
    selectedPlanet = []
    selectedWarp = []
    
    def __init__(self):
        Gtk.Window.__init__(self, title="Uplift Space Builder")
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
        self.newButton = Gtk.ToolButton(stock_id=Gtk.STOCK_NEW, label="New Level")
        self.newButton.set_expand(True)
        #self.newButton.connect("clicked", self.new_level)
        self.toolbar.add(self.newButton)

        #Stars
        self.starBox = Gtk.Box(spacing=5)
        self.starLabel = Gtk.Label("<b>Stars</b>")
        self.starLabel.set_use_markup(True)
        self.starLabel.set_alignment(0, 0)
        self.starNewButton = Gtk.ToolButton(stock_id=Gtk.STOCK_NEW, label="New Star")
        self.starNewButton.connect("clicked", self.new_star)
        self.starDelButton = Gtk.ToolButton(stock_id=Gtk.STOCK_DELETE, label="Remove Star")
        self.starDelButton.set_sensitive(False)
        #self.starDelButton.connect("clicked", self.on_star_del_clicked)
        self.starTree = Gtk.TreeView(self.create_star_model())
        self.starTree.set_rules_hint(True)
        self.starTree.get_selection().connect("changed", self.on_star_selection_changed)
        self.create_star_columns(self.starTree)

        self.box.pack_start(self.infobox, True, True, 5)
        self.infobox.pack_start(self.infoTitle, False, False, 5)
        self.infobox.pack_start(self.soundTitle, False, False, 5)
        self.infobox.pack_start(self.infoscroll, True, True, 2)
        self.infobox.pack_start(self.toolbar, False, False, 0)
        self.infoscroll.add_with_viewport(self.infoinner)
        self.starBox.pack_start(self.starLabel, True, True, 5)
        self.starBox.pack_start(self.starNewButton, False, False, 0)
        self.starBox.pack_start(self.starDelButton, False, False, 0)
        self.infoinner.pack_start(self.starBox, False, False, 5)
        self.infoinner.pack_start(self.starTree, False, False, 5)
        self.infoinner.pack_start(Gtk.HSeparator(), False, False, 5)

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

    def on_tree_selection_changed(self, selection):
        model, treeiter = selection.get_selected()
        if treeiter != None:
            self.get_level_info(model[treeiter][0])

    #STARS################

    def create_star_model(self):
        listData = []
        for i in self.stars:
            if i != []: listData.append(((i[0], i[1], i[2], i[3], i[4], i[5])))
        store = Gtk.ListStore(str, str, int, int, int, int)
        for item in listData:
            store.append(item)
        return store

    def create_star_columns(self, treeView):
        rendererText = Gtk.CellRendererText()
        column = Gtk.TreeViewColumn("Sprite", rendererText, text=0)
        column.set_sort_column_id(0)
        treeView.append_column(column)
        rendererText = Gtk.CellRendererText()
        column = Gtk.TreeViewColumn("Description", rendererText, text=1)
        column.set_sort_column_id(1)
        treeView.append_column(column)
        rendererText = Gtk.CellRendererText()
        column = Gtk.TreeViewColumn("X", rendererText, text=2)
        column.set_sort_column_id(2)
        treeView.append_column(column)
        rendererText = Gtk.CellRendererText()
        column = Gtk.TreeViewColumn("Y", rendererText, text=3)
        column.set_sort_column_id(3)
        treeView.append_column(column)
        rendererText = Gtk.CellRendererText()
        column = Gtk.TreeViewColumn("Size", rendererText, text=4)
        column.set_sort_column_id(4)
        treeView.append_column(column)
        rendererText = Gtk.CellRendererText()
        column = Gtk.TreeViewColumn("Rotation Speed", rendererText, text=5)
        column.set_sort_column_id(5)
        treeView.append_column(column)

    def on_star_selection_changed(self, selection):
        model, treeiter = selection.get_selected()
        if treeiter != None:
            outp = []
            for i in model[treeiter]:
                outp.append(i)
            self.selectedStar = outp
            self.starDelButton.set_sensitive(True)

    def new_star(self, button):
        dialog = NewStarDialog(self)
        response = dialog.run()
        if response == Gtk.ResponseType.OK:
            sprite = dialog.spriteEntry.get_text()
            desc = dialog.descEntry.get_text()
            X = int(dialog.XEntry.get_text())
            Y = int(dialog.YEntry.get_text())
            size = int(dialog.sizeEntry.get_text())
            spin = int(dialog.spinEntry.get_text())
            self.stars.append([sprite, desc, X, Y, size, spin])
        dialog.destroy()
        self.rebuild()

    def get_levels(self):
        fp = open(levelsAddress)
        levelList = []
        for i, line in enumerate(fp):
            level = json.loads(line)
            levelList.append((i, level[0]))
        fp.close()
        return levelList

    def get_level_info(self, lineNo):
        fp = open(levelsAddress)
        for i, line in enumerate(fp):
            if (i == lineNo):
                level = json.loads(line)
                self.num = i
                self.name = level[0]
                self.sound = level[1]
                self.stars = level[2]
                self.planets = level[3]
                self.warps = level[4]
                break
        fp.close()
        self.rebuild()

    def save_button_clicked(self, button):
        self.update_file(self.num)

    def rebuild(self):
        self.infoTitle.set_text("<span size='25000'><b>" + self.name + "</b></span>")
        self.infoTitle.set_use_markup(True)
        self.soundTitle.set_text("<span size='10000'><b>Music: </b>" + self.sound + "</span>")
        self.soundTitle.set_use_markup(True)
        starMod = self.create_star_model()
        self.starTree.set_model(starMod)
        #entMod = self.create_ent_model()
        #self.entTree.set_model(entMod)
        #enemyMod = self.create_enemy_model()
        #self.enemyTree.set_model(enemyMod)
        #interactableMod = self.create_interactable_model()
        #self.interactableTree.set_model(interactableMod)
        #self.entDelButton.set_sensitive(False)
        #self.doorDelButton.set_sensitive(False)
        #self.enemyDelButton.set_sensitive(False)
        #self.interactableDelButton.set_sensitive(False)
        self.show_all()

    def update_file(self, lineNo):
        with open(levelsAddress, 'r') as ff:
            data = ff.readlines()
            ff.close()
        data[lineNo] = json.dumps([self.name, self.sound, self.stars, self.planets, self.warps]) + '\n'
        with open(levelsAddress, 'w') as ff:
            ff.writelines(data)
            ff.close()

win = SpaceWindow()
win.connect("delete-event", Gtk.main_quit)
win.show_all()
Gtk.main()