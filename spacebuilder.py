#!/usr/bin/python
from gi.repository import Gtk
import json
import os

levelsAddress = "Uplift-android/assets/space.upl"

class NewLevelDialog(Gtk.Dialog):

    def __init__(self, parent):
        Gtk.Dialog.__init__(self, "New system", parent, 0,
            (Gtk.STOCK_CANCEL, Gtk.ResponseType.CANCEL,
             Gtk.STOCK_OK, Gtk.ResponseType.OK))
        box = self.get_content_area()
        nameBox = Gtk.Box(spacing=2)
        nameLabel = Gtk.Label("Name")
        self.nameEntry = Gtk.Entry()
        soundBox = Gtk.Box(spacing=2)
        soundLabel = Gtk.Label("Music")
        self.soundEntry = Gtk.Entry()
        nameBox.pack_start(nameLabel, False, False, 2)
        nameBox.pack_start(self.nameEntry, True, True, 2)
        soundBox.pack_start(soundLabel, False, False, 2)
        soundBox.pack_start(self.soundEntry, True, True, 2)
        box.pack_start(nameBox, False, False, 0)
        box.pack_start(soundBox, False, False, 0)
        self.show_all()

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
        pulseBox = Gtk.Box(spacing=2)
        pulseLabel = Gtk.Label("Pulse speed")
        self.pulseEntry = Gtk.Entry()
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
        pulseBox.pack_start(pulseLabel, False, False, 2)
        pulseBox.pack_start(self.pulseEntry, True, True, 2)
        box.pack_start(spriteBox, False, False, 0)
        box.pack_start(descBox, False, False, 0)
        box.pack_start(XBox, False, False, 0)
        box.pack_start(YBox, False, False, 0)
        box.pack_start(sizeBox, False, False, 0)
        box.pack_start(pulseBox, False, False, 0)
        self.show_all()

class NewPlanetDialog(Gtk.Dialog):

    def __init__(self, parent):
        Gtk.Dialog.__init__(self, "New planet", parent, 0,
            (Gtk.STOCK_CANCEL, Gtk.ResponseType.CANCEL,
             Gtk.STOCK_OK, Gtk.ResponseType.OK))
        box = self.get_content_area()
        descBox = Gtk.Box(spacing=2)
        descLabel = Gtk.Label("Description")
        self.descEntry = Gtk.Entry()
        spriteBox = Gtk.Box(spacing=2)
        spriteLabel = Gtk.Label("Sprite")
        self.spriteEntry = Gtk.Entry()
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
        toBox = Gtk.Box(spacing=2)
        toLabel = Gtk.Label("To level")
        self.toEntry = Gtk.Entry()
        doorBox = Gtk.Box(spacing=2)
        doorLabel = Gtk.Label("Door")
        self.doorEntry = Gtk.Entry()
        descBox.pack_start(descLabel, False, False, 2)
        descBox.pack_start(self.descEntry, True, True, 2)
        spriteBox.pack_start(spriteLabel, False, False, 2)
        spriteBox.pack_start(self.spriteEntry, True, True, 2)
        XBox.pack_start(XLabel, False, False, 2)
        XBox.pack_start(self.XEntry, True, True, 2)
        YBox.pack_start(YLabel, False, False, 2)
        YBox.pack_start(self.YEntry, True, True, 2)
        sizeBox.pack_start(sizeLabel, False, False, 2)
        sizeBox.pack_start(self.sizeEntry, True, True, 2)
        spinBox.pack_start(spinLabel, False, False, 2)
        spinBox.pack_start(self.spinEntry, True, True, 2)
        toBox.pack_start(toLabel, False, False, 2)
        toBox.pack_start(self.toEntry, True, True, 2)
        doorBox.pack_start(doorLabel, False, False, 2)
        doorBox.pack_start(self.doorEntry, True, True, 2)
        box.pack_start(descBox, False, False, 0)
        box.pack_start(spriteBox, False, False, 0)
        box.pack_start(XBox, False, False, 0)
        box.pack_start(YBox, False, False, 0)
        box.pack_start(sizeBox, False, False, 0)
        box.pack_start(spinBox, False, False, 0)
        box.pack_start(toBox, False, False, 0)
        box.pack_start(doorBox, False, False, 0)
        self.show_all()

class NewWarpDialog(Gtk.Dialog):

    def __init__(self, parent):
        Gtk.Dialog.__init__(self, "New warp", parent, 0,
            (Gtk.STOCK_CANCEL, Gtk.ResponseType.CANCEL,
             Gtk.STOCK_OK, Gtk.ResponseType.OK))
        box = self.get_content_area()
        numBox = Gtk.Box(spacing=2)
        numLabel = Gtk.Label("Number")
        self.numEntry = Gtk.Entry()
        toBox = Gtk.Box(spacing=2)
        toLabel = Gtk.Label("To system")
        self.toEntry = Gtk.Entry()
        doorBox = Gtk.Box(spacing=2)
        doorLabel = Gtk.Label("To warp #")
        self.doorEntry = Gtk.Entry()
        questBox = Gtk.Box(spacing=2)
        questLabel = Gtk.Label("Quest")
        self.questEntry = Gtk.Entry()
        geBox = Gtk.Box(spacing=2)
        geLabel = Gtk.Label(">=")
        self.geEntry = Gtk.Entry()
        XBox = Gtk.Box(spacing=2)
        XLabel = Gtk.Label("X")
        self.XEntry = Gtk.Entry()
        YBox = Gtk.Box(spacing=2)
        YLabel = Gtk.Label("Y")
        self.YEntry = Gtk.Entry()
        numBox.pack_start(numLabel, False, False, 2)
        numBox.pack_start(self.numEntry, True, True, 2)
        toBox.pack_start(toLabel, False, False, 2)
        toBox.pack_start(self.toEntry, True, True, 2)
        doorBox.pack_start(doorLabel, False, False, 2)
        doorBox.pack_start(self.doorEntry, True, True, 2)
        questBox.pack_start(questLabel, False, False, 2)
        questBox.pack_start(self.questEntry, True, True, 2)
        geBox.pack_start(geLabel, False, False, 2)
        geBox.pack_start(self.geEntry, True, True, 2)
        XBox.pack_start(XLabel, False, False, 2)
        XBox.pack_start(self.XEntry, True, True, 2)
        YBox.pack_start(YLabel, False, False, 2)
        YBox.pack_start(self.YEntry, True, True, 2)
        box.pack_start(numBox, False, False, 0)
        box.pack_start(toBox, False, False, 0)
        box.pack_start(doorBox, False, False, 0)
        box.pack_start(questBox, False, False, 0)
        box.pack_start(geBox, False, False, 0)
        box.pack_start(XBox, False, False, 0)
        box.pack_start(YBox, False, False, 0)
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
        self.newButton.connect("clicked", self.new_level)
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
        self.starDelButton.connect("clicked", self.on_star_del_clicked)
        self.starTree = Gtk.TreeView(self.create_star_model())
        self.starTree.set_rules_hint(True)
        self.starTree.connect("row-activated", self.star_row_activated)
        self.starTree.get_selection().connect("changed", self.on_star_selection_changed)
        self.create_star_columns(self.starTree)

        #Planets
        self.planetBox = Gtk.Box(spacing=5)
        self.planetLabel = Gtk.Label("<b>Planets</b>")
        self.planetLabel.set_use_markup(True)
        self.planetLabel.set_alignment(0, 0)
        self.planetNewButton = Gtk.ToolButton(stock_id=Gtk.STOCK_NEW, label="New planet")
        self.planetNewButton.connect("clicked", self.new_planet)
        self.planetDelButton = Gtk.ToolButton(stock_id=Gtk.STOCK_DELETE, label="Remove planet")
        self.planetDelButton.set_sensitive(False)
        self.planetDelButton.connect("clicked", self.on_planet_del_clicked)
        self.planetTree = Gtk.TreeView(self.create_planet_model())
        self.planetTree.set_rules_hint(True)
        self.planetTree.connect("row-activated", self.planet_row_activated)
        self.planetTree.get_selection().connect("changed", self.on_planet_selection_changed)
        self.create_planet_columns(self.planetTree)

        #Warps
        self.warpBox = Gtk.Box(spacing=5)
        self.warpLabel = Gtk.Label("<b>Warps</b>")
        self.warpLabel.set_use_markup(True)
        self.warpLabel.set_alignment(0, 0)
        self.warpNewButton = Gtk.ToolButton(stock_id=Gtk.STOCK_NEW, label="New warp")
        self.warpNewButton.connect("clicked", self.new_warp)
        self.warpDelButton = Gtk.ToolButton(stock_id=Gtk.STOCK_DELETE, label="Remove warp")
        self.warpDelButton.set_sensitive(False)
        self.warpDelButton.connect("clicked", self.on_warp_del_clicked)
        self.warpTree = Gtk.TreeView(self.create_warp_model())
        self.warpTree.set_rules_hint(True)
        self.warpTree.get_selection().connect("changed", self.on_warp_selection_changed)
        self.create_warp_columns(self.warpTree)

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
        self.planetBox.pack_start(self.planetLabel, True, True, 5)
        self.planetBox.pack_start(self.planetNewButton, False, False, 0)
        self.planetBox.pack_start(self.planetDelButton, False, False, 0)
        self.infoinner.pack_start(self.planetBox, False, False, 5)
        self.infoinner.pack_start(self.planetTree, False, False, 5)
        self.infoinner.pack_start(Gtk.HSeparator(), False, False, 5)
        self.warpBox.pack_start(self.warpLabel, True, True, 5)
        self.warpBox.pack_start(self.warpNewButton, False, False, 0)
        self.warpBox.pack_start(self.warpDelButton, False, False, 0)
        self.infoinner.pack_start(self.warpBox, False, False, 5)
        self.infoinner.pack_start(self.warpTree, False, False, 5)
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
        column = Gtk.TreeViewColumn("Pulse Speed", rendererText, text=5)
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

    def on_star_del_clicked(self, button):
        self.delete_star(self.selectedStar)

    def star_row_activated(treeview, iterr, path, data):
        os.system("eog Uplift-android/assets/gfx/" + treeview.selectedStar[0] + " &")

    def new_star(self, button):
        dialog = NewStarDialog(self)
        response = dialog.run()
        if response == Gtk.ResponseType.OK:
            sprite = dialog.spriteEntry.get_text()
            desc = dialog.descEntry.get_text()
            X = int(dialog.XEntry.get_text())
            Y = int(dialog.YEntry.get_text())
            size = int(dialog.sizeEntry.get_text())
            pulse = int(dialog.pulseEntry.get_text())
            self.stars.append([sprite, desc, X, Y, size, pulse])
        dialog.destroy()
        self.rebuild()

    def delete_star(self, star):
        for i in self.stars:
            if i == star:
                self.stars.remove(i)
        self.rebuild()

    #PLANETS##############

    def create_planet_model(self):
        listData = []
        for i in self.planets:
            if i != []: listData.append(((i[0], i[1], i[2], i[3], i[4], i[5], i[6], i[7])))
        store = Gtk.ListStore(str, str, int, int, int, int, int, int)
        for item in listData:
            store.append(item)
        return store

    def create_planet_columns(self, treeView):
        rendererText = Gtk.CellRendererText()
        column = Gtk.TreeViewColumn("Description", rendererText, text=0)
        column.set_sort_column_id(0)
        treeView.append_column(column)
        rendererText = Gtk.CellRendererText()
        column = Gtk.TreeViewColumn("Sprite", rendererText, text=1)
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
        rendererText = Gtk.CellRendererText()
        column = Gtk.TreeViewColumn("To level", rendererText, text=6)
        column.set_sort_column_id(6)
        treeView.append_column(column)
        rendererText = Gtk.CellRendererText()
        column = Gtk.TreeViewColumn("To door", rendererText, text=7)
        column.set_sort_column_id(7)
        treeView.append_column(column)

    def on_planet_selection_changed(self, selection):
        model, treeiter = selection.get_selected()
        if treeiter != None:
            outp = []
            for i in model[treeiter]:
                outp.append(i)
            self.selectedPlanet = outp
            self.planetDelButton.set_sensitive(True)

    def on_planet_del_clicked(self, button):
        self.delete_planet(self.selectedPlanet)

    def planet_row_activated(treeview, iterr, path, data):
        os.system("eog Uplift-android/assets/gfx/" + treeview.selectedPlanet[1] + " &")

    def new_planet(self, button):
        dialog = NewPlanetDialog(self)
        response = dialog.run()
        if response == Gtk.ResponseType.OK:
            desc = dialog.descEntry.get_text()
            sprite = dialog.spriteEntry.get_text()
            X = int(dialog.XEntry.get_text())
            Y = int(dialog.YEntry.get_text())
            size = int(dialog.sizeEntry.get_text())
            spin = int(dialog.spinEntry.get_text())
            to = int(dialog.toEntry.get_text())
            door = int(dialog.doorEntry.get_text())
            self.planets.append([desc, sprite, X, Y, size, spin, to, door])
        dialog.destroy()
        self.rebuild()

    def delete_planet(self, planet):
        for i in self.planets:
            if i == planet:
                self.planets.remove(i)
        self.rebuild()

    #WARPS##############

    def create_warp_model(self):
        listData = []
        for i in self.warps:
            if i != []: listData.append(((i[0], i[1], i[2], i[3], i[4], i[5], i[6])))
        store = Gtk.ListStore(int, int, int, str, int, int, int)
        for item in listData:
            store.append(item)
        return store

    def create_warp_columns(self, treeView):
        rendererText = Gtk.CellRendererText()
        column = Gtk.TreeViewColumn("Number", rendererText, text=0)
        column.set_sort_column_id(0)
        treeView.append_column(column)
        rendererText = Gtk.CellRendererText()
        column = Gtk.TreeViewColumn("To system", rendererText, text=1)
        column.set_sort_column_id(1)
        treeView.append_column(column)
        rendererText = Gtk.CellRendererText()
        column = Gtk.TreeViewColumn("To warp", rendererText, text=2)
        column.set_sort_column_id(2)
        treeView.append_column(column)
        rendererText = Gtk.CellRendererText()
        column = Gtk.TreeViewColumn("Quest", rendererText, text=3)
        column.set_sort_column_id(3)
        treeView.append_column(column)
        rendererText = Gtk.CellRendererText()
        column = Gtk.TreeViewColumn(">=", rendererText, text=4)
        column.set_sort_column_id(4)
        treeView.append_column(column)
        rendererText = Gtk.CellRendererText()
        column = Gtk.TreeViewColumn("X", rendererText, text=5)
        column.set_sort_column_id(5)
        treeView.append_column(column)
        rendererText = Gtk.CellRendererText()
        column = Gtk.TreeViewColumn("Y", rendererText, text=6)
        column.set_sort_column_id(6)
        treeView.append_column(column)

    def on_warp_selection_changed(self, selection):
        model, treeiter = selection.get_selected()
        if treeiter != None:
            outp = []
            for i in model[treeiter]:
                outp.append(i)
            self.selectedWarp = outp
            self.warpDelButton.set_sensitive(True)

    def on_warp_del_clicked(self, button):
        self.delete_warp(self.selectedWarp)

    def new_warp(self, button):
        dialog = NewWarpDialog(self)
        response = dialog.run()
        if response == Gtk.ResponseType.OK:
            num = int(dialog.numEntry.get_text())
            to = int(dialog.toEntry.get_text())
            door = int(dialog.doorEntry.get_text())
            quest = dialog.questEntry.get_text()
            ge = int(dialog.geEntry.get_text())
            X = int(dialog.XEntry.get_text())
            Y = int(dialog.YEntry.get_text())
            self.warps.append([num, to, door, quest, ge, X, Y])
        dialog.destroy()
        self.rebuild()

    def delete_warp(self, warp):
        for i in self.warps:
            if i == warp:
                self.warps.remove(i)
        self.rebuild()

    #LEVELS################

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
        planetMod = self.create_planet_model()
        self.planetTree.set_model(planetMod)
        warpMod = self.create_warp_model()
        self.warpTree.set_model(warpMod)
        self.planetDelButton.set_sensitive(False)
        self.warpDelButton.set_sensitive(False)
        self.show_all()

    def new_level(self, button):
        dialog = NewLevelDialog(self)
        response = dialog.run()
        if response == Gtk.ResponseType.OK:
            name = dialog.nameEntry.get_text()
            sound = dialog.soundEntry.get_text()
            fp = open(levelsAddress, 'a')
            fp.write(json.dumps([name, sound, [], [], []]) + '\n')
            fp.close()
            model = self.create_model()
            self.treeView.set_model(model)
        dialog.destroy()

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