package com.example.dea.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.example.dea.R;
import com.example.dea.entity.Cell;
import com.example.dea.util.SharedPreferenceManager;

import java.util.ArrayList;

public class TableAdapter  extends AbstractTableAdapter<Cell.ColumnHeader, Cell.RowHeader, Cell> {
    private Context context;

    public TableAdapter(Context context) {
        this.context=context;
    }

    /**
     *      CELL
     */
    class MyCellViewHolder extends AbstractViewHolder {

        final LinearLayout cell_container;
        final TextView cell_textview;

        public MyCellViewHolder(View itemView) {
            super(itemView);
            cell_container = itemView.findViewById(R.id.cell_container);
            cell_textview = itemView.findViewById(R.id.cell_data);

        }
    }

    @Override
    public AbstractViewHolder onCreateCellViewHolder(ViewGroup parent, int viewType) {
        // Get cell xml layout
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_view_cell_layout, parent, false);
        // Create a Custom ViewHolder for a Cell item.
        return new MyCellViewHolder(layout);
    }
    @Override
    public void onBindCellViewHolder(AbstractViewHolder holder, Cell cellItemModel, int
            columnPosition, int rowPosition) {
        Cell cell = (Cell) cellItemModel;

        // Get the holder to update cell item text
        MyCellViewHolder viewHolder = (MyCellViewHolder) holder;
        viewHolder.cell_textview.setText(cell.getData());

        // If your TableView should have auto resize for cells & columns.
        // Then you should consider the below lines. Otherwise, you can ignore them.

        // It is necessary to remeasure itself.
        viewHolder.cell_container.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
        viewHolder.cell_textview.requestLayout();
    }


    /**
     *      COLUMN HEADER
     */
    class MyColumnHeaderViewHolder extends AbstractViewHolder {

        final LinearLayout column_header_container;
        final TextView column_header_textview;

        public MyColumnHeaderViewHolder(View itemView) {
            super(itemView);
            column_header_container = itemView.findViewById(R.id.column_header_container);
            column_header_textview = itemView.findViewById(R.id.column_header_textView);
        }
    }
    @Override
    public AbstractViewHolder onCreateColumnHeaderViewHolder(ViewGroup parent, int viewType) {

        // Get Column Header xml Layout
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_view_column_header_layout, parent, false);

        // Create a ColumnHeader ViewHolder
        return new MyColumnHeaderViewHolder(layout);
    }
    @Override
    public void onBindColumnHeaderViewHolder(AbstractViewHolder holder, Cell.ColumnHeader columnHeaderItemModel, int
            position) {
        Cell.ColumnHeader columnHeader = (Cell.ColumnHeader) columnHeaderItemModel;
        MyColumnHeaderViewHolder columnHeaderViewHolder = (MyColumnHeaderViewHolder) holder;
        columnHeaderViewHolder.column_header_textview.setText(columnHeader.getData());

        // If your TableView should have auto resize for cells & columns.
        // Then you should consider the below lines. Otherwise, you can ignore them.
        // It is necessary to remeasure itself.
        columnHeaderViewHolder.column_header_container.getLayoutParams().width = LinearLayout
                .LayoutParams.WRAP_CONTENT;
        columnHeaderViewHolder.column_header_textview.requestLayout();
    }



    /**
     *      ROW HEADER
     */
    class MyRowHeaderViewHolder extends AbstractViewHolder {

        final TextView row_header_textview;

        public MyRowHeaderViewHolder(View itemView) {
            super(itemView);
            row_header_textview = itemView.findViewById(R.id.row_header_textView);
        }
    }
    @Override
    public AbstractViewHolder onCreateRowHeaderViewHolder(ViewGroup parent, int viewType) {

        // Get Row Header xml Layout
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_view_row_header_layout, parent, false);

        // Create a Row Header ViewHolder
        return new MyRowHeaderViewHolder(layout);
    }
    @Override
    public void onBindRowHeaderViewHolder(AbstractViewHolder holder, Cell.RowHeader rowHeaderItemModel, int
            position) {
        Cell.RowHeader rowHeader = (Cell.RowHeader) rowHeaderItemModel;
        MyRowHeaderViewHolder rowHeaderViewHolder = (MyRowHeaderViewHolder) holder;
        rowHeaderViewHolder.row_header_textview.setText(rowHeader.getData());
    }


    /**
     *      CORNER
     */
    @Override
    public View onCreateCornerView(ViewGroup parent) {
        // Get Corner xml layout
        return LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_view_corner_layout, parent, false);
    }

    @Override
    public int getColumnHeaderItemViewType(int position) {
        return 0;
    }

    @Override
    public int getRowHeaderItemViewType(int position) {
        return 0;
    }

    @Override
    public int getCellItemViewType(int position) {
        return 0;
    }





}