package net.lmlab.m_tsunami_android.entity

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name= "feed", strict = false)
class FeedEntity {

    @set:Element(name = "title", required = false)
    @get:Element(name = "title", required = false)
    var title: String? = ""

    @set:ElementList(entry = "entry", inline = true)
    @get:ElementList(entry = "entry", inline = true)
    var entities: List<EntryEntity>? = null
}

@Root(name = "entry", strict = false)
class EntryEntity {

    @set:Element(name = "title", required = true)
    @get:Element(name = "title", required = true)
    var title: String = ""

    @set:Element(name = "id", required = true)
    @get:Element(name = "id", required = true)
    var id: String = ""

    @set:Element(name = "content", required = true)
    @get:Element(name = "content", required = true)
    var content: String = ""

    @set:Element(name = "updated", required = false)
    @get:Element(name = "updated", required = false)
    var updated: String? = null

    @set:Element(name = "link", required = false)
    @get:Element(name = "link", required = false)
    var link: String? = null
}
