package yuntech.b10517012.visualchat.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import yuntech.b10517012.visualchat.R
import yuntech.b10517012.visualchat.model.WordModel
import yuntech.b10517012.visualchat.sqlite.MyWordDAO
import yuntech.b10517012.visualchat.ui.IEditWord


class AlertEditWord {

    lateinit var updateCallBack: IEditWord

    fun addAndMod(index: Long, word: String, context: Context, myWordDAO: MyWordDAO){

        updateCallBack = context as IEditWord

        val itemView = LayoutInflater.from(context).inflate(R.layout.alert_add_word, null)
        val edtAdd = itemView.findViewById<EditText>(R.id.edt_add_input)
        val title: String
        val btnText: String
        edtAdd.setText(word)
        if (word == ""){
            title =  context.getString(R.string.new_word)
            btnText = context.getString(R.string.add)
        }else{
            title = context.getString(R.string.mod_word)
            btnText = context.getString(R.string.mod)
        }

        val builder = AlertDialog.Builder(context)
            .setTitle(title)
            .setView(itemView)
            .setPositiveButton(btnText){ _, _ ->
                if (title == context.getString(R.string.new_word) && !edtAdd.text.toString().equals("")){
                    myWordDAO.insert(WordModel(0, edtAdd.text.toString(), myWordDAO.getLargestOrder()+1))
                }else if(title == context.getString(R.string.mod_word) && !edtAdd.text.toString().equals("")){
                    myWordDAO.update(WordModel(index, edtAdd.text.toString(), 0))
                }
                updateCallBack.updateAsDialogFinish()

                val imm =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(edtAdd.windowToken, 0)
            }
        // Soft keyboard auto show up
        val dialog: AlertDialog = builder.create()
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        edtAdd.requestFocus()
        dialog.show()
    }
}