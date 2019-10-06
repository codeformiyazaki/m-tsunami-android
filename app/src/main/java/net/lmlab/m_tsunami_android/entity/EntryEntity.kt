package net.lmlab.m_tsunami_android.entity

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "entry", strict = false)
class EntryEntity {

    @set:Element(name = "title", required = true)
    @get:Element(name = "title", required = true)
    var title: String? = null

    @set:Element(name = "content", required = false)
    @get:Element(name = "content", required = false)
    var content: String? = null

    @set:Element(name = "updated", required = false)
    @get:Element(name = "updated", required = false)
    var updated: String? = null

    @set:Element(name = "link", required = false)
    @get:Element(name = "link", required = false)
    var link: String? = null
}
