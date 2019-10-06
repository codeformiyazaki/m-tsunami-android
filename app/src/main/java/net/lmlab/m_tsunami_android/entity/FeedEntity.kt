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
