package np.com.susanthapa.customlintcheck

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Toast.makeText(this, "hello", Toast.LENGTH_LONG)
        // log the onCreate method
        Log.d("onCreate", "onCreate Called")
    }
}