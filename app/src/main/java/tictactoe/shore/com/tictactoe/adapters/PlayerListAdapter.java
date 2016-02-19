package tictactoe.shore.com.tictactoe.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import tictactoe.shore.com.tictactoe.R;
import tictactoe.shore.com.tictactoe.model.Player;

/**
 * Created by Shiva on 2/19/2016.
 */
public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.CustomViewHolder> {

    /**
     * Called when RecyclerView needs a new {@link RecyclerView.ViewHolder} of the given type to represent
     * an item.
     * <p/>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p/>
     * The new ViewHolder will be used to display items of the adapter using
     * onBindViewHolder(). Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     */

    private List<Player> mPlayers;
    private Context mContext;

    public PlayerListAdapter(List<Player> mPlayers, Context mContext) {
        this.mPlayers = mPlayers;
        this.mContext = mContext;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_player_list_item, parent, false);
        return new CustomViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link RecyclerView.ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p/>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link RecyclerView.ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p/>
     * Override  instead if Adapter can
     * handle effcient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Player player = mPlayers.get(position);
        holder.playerNameTextView.setText(player.getName());
        holder.playerScoreTextView.setText(String.format(mContext.getResources().getString(R.string.player_score), player.getScore()));
    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return (null != mPlayers ? mPlayers.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView playerNameTextView, playerScoreTextView;

        public CustomViewHolder(View itemView) {
            super(itemView);
            playerNameTextView = (TextView) itemView.findViewById(R.id.TextView_memberName);
            playerScoreTextView = (TextView) itemView.findViewById(R.id.TextView_memberScore);
        }
    }
}
