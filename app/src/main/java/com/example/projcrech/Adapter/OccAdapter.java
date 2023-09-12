package com.example.projcrech.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.util.ArrayMap;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projcrech.StatusLunchSnackData;
import com.example.projcrech.StatusLunchSnackAdapter;
import com.example.projcrech.Model.Child;
import com.example.projcrech.Model.Occurrence;
import com.example.projcrech.R;
import com.example.projcrech.database.database_helper;

import java.util.ArrayList;
import java.util.List;

public class OccAdapter extends RecyclerView.Adapter<OccAdapter.LunchViewHolder> {

    private LayoutInflater layoutInflater;
    private final Context context;
    private List<Occurrence> occurrenceList;
    MyClickListener listener;
    ArrayList<Child> list;
    OccListAdapter adapter = null;
    public static database_helper db2;
    private List<String> mSelectedItems;
    private StatusLunchSnackAdapter madapter;
    String currentStatus="";


    public OccAdapter(Context context, List<Occurrence> occurrenceList, MyClickListener listener) {
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context=context;
        this.occurrenceList = occurrenceList;
        this.listener=listener;
        db2=new database_helper(context);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @NonNull
    @Override
    public LunchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_box_content, parent, false);
        db2=new database_helper(context);
        list = new ArrayList<>();
        adapter = new OccListAdapter(context, R.layout.custom_box_content, (ArrayList<Occurrence>) occurrenceList);
        LunchViewHolder holder = new LunchViewHolder(view, new MyClickListener() {
            @Override
            public void onEdit(int p) {
                Cursor c = db2.getData("SELECT ID_OCCURRENCE,NAME_OCCURRENCE FROM Occurrence_table");
                ArrayMap<Integer,String> occList = new ArrayMap<Integer,String>();
                ArrayList<Integer> arrID2 = new ArrayList<Integer>();
                ArrayList<String> arrID = new ArrayList<String>();
                while (c.moveToNext()) {
                    arrID.add(c.getString(1));
                    arrID2.add(c.getInt(0));
                }

                Log.i("test3", String.valueOf(arrID2.get(p)));
                Log.i("test2", arrID.get(p));
            }

            @Override
            public void onDelete(int p) {
                // delete
                /*Cursor c = db2.getData("SELECT ID_OCCURRENCE FROM Occurrence_table");
                ArrayList<Integer> arrID = new ArrayList<Integer>();
                while (c.moveToNext()) {
                    arrID.add(c.getInt(0));


                }
                int idd=arrID.get(p);
                Toast.makeText(context, String.valueOf(idd), Toast.LENGTH_SHORT).show();
              //  showDialogDelete(arrID.get(p));*/
            }

        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LunchViewHolder holder, final int position) {
        final Occurrence occurrence=occurrenceList.get(position);
        holder.mName.setText(occurrence.getContentOcc());
        holder.mContent.setText(occurrence.getDateOcc());

        db2=new database_helper(context);
        list = new ArrayList<>();

        holder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogUpdate(occurrence.getIdOcc(),
                        occurrence.getNameOcc(),
                        occurrence.getContentOcc(),
                        occurrence.getDateOcc());
            }
        });

        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDelete(occurrence.getIdOcc(),
                        occurrence.getNameOcc(),
                        occurrence.getContentOcc(),
                        occurrence.getDateOcc());
            }
        });
    }

    @Override
    public int getItemCount() {
        return occurrenceList.size();
    }



    public static class LunchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        private TextView mName, mContent;
        private ImageButton mEdit,mDelete;
        MyClickListener listener;

        public LunchViewHolder(@NonNull View itemView, MyClickListener listener) {
            super(itemView);
            mName = itemView.findViewById(R.id.txtContent1);
            mContent = itemView.findViewById(R.id.txtSchedule1);
            mEdit = itemView.findViewById(R.id.buttonModify);
            mDelete = itemView.findViewById(R.id.buttonDelete);

            this.listener = listener;

            mEdit.setOnClickListener(this);
            mDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.buttonModify:
                    listener.onEdit(this.getLayoutPosition());
                    break;
                case R.id.buttonDelete:
                    listener.onDelete(this.getLayoutPosition());
                    break;
                default:
                    break;
            }
        }
    }

    private void showDialogUpdate(final int idOcc, String nameOcc, String contentOcc, String hourOcc) {
        if(nameOcc.equals("Muda de fralda")) {
            showDialogUpdateDiaper(idOcc, nameOcc, contentOcc, hourOcc);
        }

        if(nameOcc.equals("Obs_saude")) {
            showDialogUpdateHealth(idOcc, nameOcc, contentOcc, hourOcc);
        }

        if(nameOcc.equals("Almoço") || nameOcc.equals("Lanche")) {
            showDialogUpdateLunchSnack(idOcc, nameOcc, contentOcc, hourOcc);
        }
    }


    private void showDialogUpdateHealth(final int idOccHealth, String nameOccHealth, String contentOccHealth, String hourOccHealth) {

        final Dialog dialogHealth = new Dialog(context,R.style.DialogStyle);
        dialogHealth.setContentView(R.layout.update_health_activity);
        ImageView imgEdit=dialogHealth.findViewById(R.id.imageViewFood);
        imgEdit.setImageResource(R.drawable.ic_edit_occ);
        TextView txtDesc = dialogHealth.findViewById(R.id.txtDesc);
        TextView txtTitle = dialogHealth.findViewById(R.id.txttite);
        txtTitle.setText("Modificação da ocorrência\n");
        txtDesc.setText("Deseja modificar a ocorrência selecionada?\n\nOcorrência: Observação de saúde\n"
                +"Situação: "+contentOccHealth+"\n"+"Hora: "+hourOccHealth+"\n");
        txtDesc.setGravity(Gravity.LEFT);
        final EditText edtObsHealth = (EditText) dialogHealth.findViewById(R.id.InputHealth);
        edtObsHealth.setText(contentOccHealth);
        edtObsHealth.setTextColor(Color.BLACK);
        Button btnYes = dialogHealth.findViewById(R.id.btn_yes);
        Button btnNo = dialogHealth.findViewById(R.id.btn_no);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    db2.updateOcc(
                        edtObsHealth.getText().toString().trim(),
                        idOccHealth
                );

            } catch (Exception e) {
                Log.e("error", e.getMessage());
            }
                Toast.makeText(context, "Modificado com sucesso!",Toast.LENGTH_SHORT).show();
                dialogHealth.dismiss();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogHealth.dismiss();
            }
        });
        dialogHealth.setCancelable(false);
        dialogHealth.getWindow().setBackgroundDrawableResource(R.drawable.bg_window_update_delete);
        dialogHealth.show();
    }




    private void showDialogUpdateDiaper(final int idOccDiaper, String nameOccDiaper, String contentOccDiaper, String hourOccDiaper){
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_update_diaper, null);
        ImageView imgEdit=view.findViewById(R.id.img_icon);
        imgEdit.setImageResource(R.drawable.ic_delete_occ);
        imgEdit.setImageResource(R.drawable.ic_edit_occ);
        TextView txtDesc = view.findViewById(R.id.txtDesc);
        TextView txtTitle = view.findViewById(R.id.txttite);
        dialogDelete.setCustomTitle(view);
        txtTitle.setText("Modificação da ocorrência\n");
        txtDesc.setText("Deseja modificar a ocorrência selecionada?\n\nOcorrência: "+nameOccDiaper+
                "\n"+"Situação: "+contentOccDiaper+
                "\n"+"Hora: "+hourOccDiaper+"\n");
        txtDesc.setGravity(Gravity.LEFT);

        mSelectedItems = new ArrayList<>();
        dialogDelete.setMultiChoiceItems(R.array.diaperList, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i, boolean isChecked) {
                String[] items = context.getResources().getStringArray(R.array.diaperList);
                if (isChecked) {
                    mSelectedItems.add(items[i]);


                } else if (mSelectedItems.contains(items[i])) {
                    mSelectedItems.remove(items[i]);
                }
            }

        });


        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String final_selection = "";

                for (String item : mSelectedItems) {
                    final_selection = final_selection + "\n" + item;

                    if(item.equals("Xixi") && !item.equals("Cocó (Sanita)") && !item.equals("Cocó (Fralda)")) {

                        db2.updateOcc("Fez xixi", idOccDiaper);
                    }
                    else if(!item.equals("Xixi") && item.equals("Cocó (Sanita)") && !item.equals("Cocó (Fralda)"))
                    {
                        db2.updateOcc("Fez cocó na sanita", idOccDiaper);
                    }
                    else if(!item.equals("Xixi") && !item.equals("Cocó (Sanita)") && item.equals("Cocó (Fralda)"))
                    {
                        db2.updateOcc("Fez cocó na fralda", idOccDiaper);
                    }

                    else if(item.equals("Xixi") && item.equals("Cocó (Sanita)") && !item.equals("Cocó (Fralda)"))
                    {
                        db2.updateOcc("Fez xixi e cocó na sanita", idOccDiaper);
                    }

                    else if(item.equals("Xixi") && !item.equals("Cocó (Sanita)") && item.equals("Cocó (Fralda)"))
                    {
                        db2.updateOcc("Fez xixi e cocó na sanita", idOccDiaper);
                    }
                    else if(item.equals("Xixi") && item.equals("Cocó (Sanita)") && item.equals("Cocó (Fralda)"))
                    {
                        db2.updateOcc("Fez xixi e cocó na fralda", idOccDiaper);

                    }
                    else if(!item.equals("Xixi") && item.equals("Cocó (Sanita)") && item.equals("Cocó (Fralda)")) {

                        Toast.makeText(context, "Impossivel! Tente outras opções", Toast.LENGTH_SHORT).show();
                    }

                    else if(!item.equals("Xixi") && !item.equals("Cocó (Sanita)") && !item.equals("Cocó (Fralda)")) {

                        Toast.makeText(context, "Selecione uma ou mais opções", Toast.LENGTH_SHORT).show();
                    }
                }
                Toast.makeText(context, "Modificado com sucesso!", Toast.LENGTH_SHORT).show();
            }
        });

        dialogDelete.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        dialogDelete.show();
    }


    private void showDialogUpdateLunchSnack(final int idOcc, String nameOcc, String contentOcc, String hourOcc) {
        final Dialog dialogLunchSnack =new Dialog(context,R.style.DialogStyle);
        dialogLunchSnack.setContentView(R.layout.custom_update_lunch_snack);
        ImageView imgEdit=dialogLunchSnack.findViewById(R.id.img_icon);
        imgEdit.setImageResource(R.drawable.ic_edit_occ);
        TextView txtDesc = dialogLunchSnack.findViewById(R.id.txtDesc);
        TextView txtTitle = dialogLunchSnack.findViewById(R.id.txttite);
        txtTitle.setText("Modificação da ocorrência\n");
        txtDesc.setText("Deseja modificar a ocorrência selecionada?\n\nOcorrência: "+nameOcc+"\n"
                +"Situação: "+contentOcc+"\n"+"Hora: "+hourOcc+"\n");
        txtDesc.setGravity(Gravity.LEFT);
        Button btnYes = dialogLunchSnack.findViewById(R.id.btn_yes);
        Button btnNo = dialogLunchSnack.findViewById(R.id.btn_no);
        Spinner mSpinner=dialogLunchSnack.findViewById(R.id.spinner);
        madapter = new StatusLunchSnackAdapter(context, StatusLunchSnackData.getStatusList());
        mSpinner.setAdapter(madapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                currentStatus = StatusLunchSnackData.getStatusList().get(pos).getName();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    db2.updateOcc(
                            currentStatus,
                            idOcc
                    );

                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }
                dialogLunchSnack.dismiss();
                Toast.makeText(context, "Modificado com sucesso!",Toast.LENGTH_SHORT).show();

            }
        });


        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLunchSnack.dismiss();
            }
        });
        dialogLunchSnack.setCancelable(false);
        dialogLunchSnack.getWindow().setBackgroundDrawableResource(R.drawable.bg_window_update_delete);
        dialogLunchSnack.show();
    }



    private void showDialogDelete(final int idOcc, String nameOcc, String contentOcc, String hourOcc) {
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(context,R.style.DialogStyle);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_dialog_confirmation, null);
        Button btnYes = view.findViewById(R.id.btn_yes);
        Button btnNo = view.findViewById(R.id.btn_no);
        ImageView imgDelete = view.findViewById(R.id.img_icon);
        TextView txtDesc = view.findViewById(R.id.txtDesc);
        TextView txtTitle = view.findViewById(R.id.txttite);
        imgDelete.setImageResource(R.drawable.ic_delete_occ);
        txtTitle.setText("Eliminação da ocorrência\n");

        if(nameOcc.equals("Obs_saude")){
            txtDesc.setText("Deseja eliminar a ocorrência selecionada?\n\nOcorrência: Observação de saúde\n"
                    +"Situação: "+contentOcc
                    +"\n"+"Hora: "+hourOcc);

        } else {
            txtDesc.setText("Deseja eliminar a ocorrência selecionada?\n\nOcorrência: "+nameOcc
                    +"\n"+"Situação: "+contentOcc
                    +"\n"+"Hora: "+hourOcc);

        }

        txtDesc.setGravity(Gravity.LEFT);
        dialogDelete.setView(view);
        btnYes.setVisibility(View.GONE);
        btnNo.setVisibility(View.GONE);

        dialogDelete.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    db2.deleteOcc(idOcc);
                    Toast.makeText(context, "Apagado a ocorrência com sucesso", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }
                // updateOccList();
                //notifyItemRangeInserted(0, occurrenceList.size());
                occurrenceList.clear();
                notifyDataSetChanged();

            }
        });

        dialogDelete.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();

    }


    public interface MyClickListener {
        void onEdit(int p);
        void onDelete(int p);
    }
}