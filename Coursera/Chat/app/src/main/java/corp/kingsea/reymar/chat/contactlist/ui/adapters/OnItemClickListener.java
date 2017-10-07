package corp.kingsea.reymar.chat.contactlist.ui.adapters;

import corp.kingsea.reymar.chat.entities.User;

/**
 * Created by reyma on 24/06/2016.
 */
public interface OnItemClickListener {
    void onItemClickListener(User user);
    void onItemLongClick(User user);
}

