package net.lmlab.m_tsunami_android.entity

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name= "Report", strict = false)
class ReportEntity {

    @set:Element(name = "Head", required = true)
    @get:Element(name = "Head", required = true)
    var head: HeadEntity? = HeadEntity()
}

@Root(name= "Head", strict = false)
class HeadEntity {

    @set:Element(name = "Title", required = false)
    @get:Element(name = "Title", required = false)
    var title: String? = null

    @set:Element(name = "Headline", required = false)
    @get:Element(name = "Headline", required = false)
    var headline: HeadlineEntity? = null
}

@Root(name= "Headline", strict = false)
class HeadlineEntity {

    @set:Element(name = "Text", required = false)
    @get:Element(name = "Text", required = false)
    var text: String? = null

    @set:ElementList(entry = "Information", inline = true)
    @get:ElementList(entry = "Information", inline = true)
    var informations: List<InformationEntity>? = null
}

@Root(name= "Information", strict = false)
class InformationEntity {

    @set:Attribute(name = "type")
    @get:Attribute(name = "type")
    var type: String = ""

    @set:ElementList(entry = "Item", inline = true)
    @get:ElementList(entry = "Item", inline = true)
    var items: List<ItemEntity>? = null
}

@Root(name= "Item", strict = false)
class ItemEntity {

    @set:Element(name = "Kind", required = true)
    @get:Element(name = "Kind", required = true)
    var kind: KindEntity = KindEntity()

    @set:Element(name = "Areas", required = true)
    @get:Element(name = "Areas", required = true)
    var areas: AreasEntity? = AreasEntity()
}

@Root(name= "Kind", strict = false)
class KindEntity {

    @set:Element(name = "Name", required = false)
    @get:Element(name = "Name", required = false)
    var name: String? = null
}

@Root(name= "Areas", strict = false)
class AreasEntity {

    @set:ElementList(entry = "Area", inline = true)
    @get:ElementList(entry = "Area", inline = true)
    var areas: List<AreaEntity>? = null
}

@Root(name= "Area", strict = false)
class AreaEntity {

    @set:Element(name = "Name", required = true)
    @get:Element(name = "Name", required = true)
    var name: String = ""

    @set:Element(name = "Code", required = false)
    @get:Element(name = "Code", required = false)
    var text: String? = null
}