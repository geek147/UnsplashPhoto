package com.envious.searchphoto.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.envious.data.local.imagecache.ImageLoader
import com.envious.domain.model.Photo
import com.envious.searchphoto.R
import com.envious.searchphoto.databinding.ListItemRowBinding

class ItemAdapter(private val context: Context) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    private var listItem: MutableList<Photo> = mutableListOf()
    private val guidToIdMap = GuidToIdMap()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item_row, parent, false)
        )
    }

    override fun getItemId(position: Int): Long {
        val item: Photo = listItem[position]
        return guidToIdMap.getIdByGuid(item.id)
    }

    fun addData(list: List<Photo>) {
        this.listItem.addAll(list)
        notifyDataSetChanged()
    }

    fun setList(list: List<Photo>) {
        this.listItem.clear()
        this.listItem.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holderItem: ItemViewHolder, position: Int) {
        with(holderItem) {
            ImageLoader.with(context).load(binding.ivMoviePoster, listItem[position].urls.regular)
        }
    }

    override fun getItemCount(): Int {
        return if (listItem.isNullOrEmpty()) {
            0
        } else {
            listItem.size
        }
    }

    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val binding = ListItemRowBinding.bind(view)
    }

    /*
    Generate unique id
     */
    class GuidToIdMap {
        private val guidToIdMapping: MutableMap<String, Long> = HashMap()
        private val idToGuidMapping: MutableMap<Long, String> = HashMap()
        private var idGenerator: Long = 0
        fun getIdByGuid(guid: String): Long {
            if (!guidToIdMapping.containsKey(guid)) {
                val id = idGenerator++
                guidToIdMapping[guid] = id
                idToGuidMapping[id] = guid
            }
            return guidToIdMapping[guid]!!
        }

        fun getGuidById(id: Long): String? {
            return if (idToGuidMapping.containsKey(id)) {
                idToGuidMapping[id]
            } else null
        }
    }
}
