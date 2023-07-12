package com.example.naviku_versi_karisma.ui.detail_kode_pribadi

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.caverock.androidsvg.SVG
import com.example.naviku_versi_karisma.R
import com.example.naviku_versi_karisma.data.local.Code
import com.example.naviku_versi_karisma.databinding.ActivityPersonalCodeDetailBinding
import com.example.naviku_versi_karisma.helper.ViewModelFactory
import com.example.naviku_versi_karisma.ui.kode_pribadi.PersonalCodeListActivity
import com.example.naviku_versi_karisma.ui.main.MainActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfPageEventHelper
import com.itextpdf.text.pdf.PdfWriter
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class PersonalCodeDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CODE = "extra_code"
    }

    private var _activityPersonalCodeDetailBinding: ActivityPersonalCodeDetailBinding? = null
    private val binding get() = _activityPersonalCodeDetailBinding

    private lateinit var personalCodeDetailViewModel: PersonalCodeDetailViewModel

    private var code: Code? = null

    private val createPdfLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data
                if (uri != null) {
                    savePDF(uri)
                } else {
                    Toast.makeText(this, "Gagal membuat file PDF", Toast.LENGTH_SHORT).show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityPersonalCodeDetailBinding = ActivityPersonalCodeDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        personalCodeDetailViewModel = obtainViewModel(this@PersonalCodeDetailActivity)

        @Suppress("DEPRECATION")
        code = intent.getParcelableExtra(EXTRA_CODE)


        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),1)
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)

        if (code != null) {
            code?.let { code ->
                binding?.tvCodeDesc?.text = code.name

                val writer = QRCodeWriter()
                try {
                    val hints = Hashtable<EncodeHintType, Any>()
                    hints[EncodeHintType.MARGIN] = 0 // Set margin ke 0
                    val bitMatrix = writer.encode(code.name, BarcodeFormat.QR_CODE, 512, 512, hints)
                    val width = bitMatrix.width
                    val height = bitMatrix.height
                    val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
                    for (x in 0 until width) {
                        for (y in 0 until height) {
                            bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                        }
                    }
                    binding?.ivCodeImg?.setImageBitmap(bmp)
                } catch (e: WriterException) {
                    e.printStackTrace()
                }
            }
        }


        binding?.btnDownloadCodeDetail?.setOnClickListener {
            createPDFFile()
        }

        binding?.btnDeleteCodeDetail?.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(this@PersonalCodeDetailActivity)

            // Mengatur tampilan kustom dari layout XML
            val layoutInflater = LayoutInflater.from(this@PersonalCodeDetailActivity)
            val view = layoutInflater.inflate(R.layout.custom_alert_dialog, null)
            alertDialogBuilder.setView(view)

            // Dapatkan referensi ke elemen UI dalam tampilan kustom Anda
            val btnPositive = view.findViewById<Button>(R.id.btn_positive)
            val btnNegative = view.findViewById<Button>(R.id.btn_negative)
            val dialogMessage = view.findViewById<TextView>(R.id.tv_dialog)

            // Atur teks judul dan pesan
            dialogMessage.text = "Apakah Anda yakin ingin menghapus kode ini?"

            val alertDialog = alertDialogBuilder.create()

            // Atur onClickListener untuk tombol positif
            btnPositive.setOnClickListener { view ->
                // Kode yang akan dijalankan saat tombol "Hapus" diklik
                personalCodeDetailViewModel.delete(code as Code)
                showToast(getString(R.string.deleted))

                val intent = Intent(this@PersonalCodeDetailActivity, PersonalCodeListActivity::class.java)
                startActivity(intent)

                alertDialog.dismiss()
            }

            // Atur onClickListener untuk tombol negatif
            btnNegative.setOnClickListener { view ->
                alertDialog.dismiss()
            }

            // Menampilkan AlertDialog dengan tampilan kustom
            alertDialog.show()
        }

        binding?.btnBackCodeDetail?.setOnClickListener {
            val intent = Intent(this@PersonalCodeDetailActivity, PersonalCodeListActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getImageOfView(): Bitmap? {
        val bitmap = Bitmap.createBitmap(binding?.ivCodeImg?.width ?: 0, binding?.ivCodeImg?.height ?: 0, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        binding?.ivCodeImg?.draw(canvas)
        return bitmap
    }


    private fun createPDFFile() {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "application/pdf"
        intent.putExtra(Intent.EXTRA_TITLE, generateFileName())

        createPdfLauncher.launch(intent)
        finish()
    }

    private fun savePDF(uri: Uri) {
        try {
            contentResolver.openOutputStream(uri)?.use { outputStream ->
                val mDoc = Document(PageSize.A3, 0f, 0f, 100f, 0f) // Mengatur margin menjadi 0
                val writer = PdfWriter.getInstance(mDoc, outputStream)
                val footerFont = Font(Font.FontFamily.HELVETICA, 10f, Font.NORMAL, BaseColor.BLACK)


                val eventHandler = object : PdfPageEventHelper() {
                    override fun onEndPage(writer: PdfWriter?, document: Document?) {
                        val pdfContentByte = writer?.directContent

                        val svgInputStream = resources.openRawResource(R.raw.footer) // Ganti dengan ID sumber daya SVG Anda
                        val svg = SVG.getFromInputStream(svgInputStream)
                        val picture = svg.renderToPicture()
                        val footerBitmap = Bitmap.createBitmap(picture.width, picture.height, Bitmap.Config.ARGB_8888)
                        val canvas = Canvas(footerBitmap)
                        canvas.drawPicture(picture)

                        val outputStream = ByteArrayOutputStream()
                        footerBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                        val footerByteArray = outputStream.toByteArray()
                        val footerImage = Image.getInstance(footerByteArray)

                        footerImage.scaleToFit(document?.pageSize?.width ?: 0f, footerImage.height.toFloat()) // Gambar akan memenuhi lebar dokumen dan mengikuti tinggi asli gambar
                        footerImage.setAbsolutePosition(0f, 0f)

                        pdfContentByte?.addImage(footerImage)
                    }
                }

                writer.setPageEvent(eventHandler)

                mDoc.open()

                mDoc.addAuthor("Iqbal")

                val qrCodeWidthCm = 25f
                val qrCodeHeightCm = 25f

                // Convert cm to points (1 cm = 28.35 points)
                val qrCodeWidthPoints = qrCodeWidthCm * 28.35f
                val qrCodeHeightPoints = qrCodeHeightCm * 28.35f

                val bitmap: Bitmap? = getImageOfView()
                val scaledBitmap = bitmap?.let { Bitmap.createScaledBitmap(it, qrCodeWidthPoints.toInt(), qrCodeHeightPoints.toInt(), false) }
                val stream = ByteArrayOutputStream()
                scaledBitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val byteArray = stream.toByteArray()
                val image = Image.getInstance(byteArray)
                // Menentukan posisi gambar sebagai tengah
                val documentWidth = mDoc.pageSize.width
                val documentHeight = mDoc.pageSize.height
                val imageWidth = image.width.toFloat()
                val imageHeight = image.height.toFloat()
                val offsetX = (documentWidth - imageWidth) / 2
                val offsetY = (documentHeight - imageHeight) / 2 + (documentHeight * 0.2).toFloat() - 70f

                image.setAbsolutePosition(offsetX, offsetY)

                mDoc.add(image)

                val textpdf = binding?.tvCodeDesc?.text.toString()
                val textFont = Font(Font.FontFamily.HELVETICA, 30f, Font.BOLD, BaseColor.BLACK)

                // Mendapatkan lebar dan tinggi halaman dokumen
                val pageWidth = mDoc.pageSize.width
                val pageHeight = mDoc.pageSize.height

                // Mendapatkan lebar dan tinggi teks
                val textWidth = textFont.getCalculatedBaseFont(true).getWidthPoint(textpdf, textFont.size)
                val textHeight = textFont.getCalculatedBaseFont(true).getAscentPoint(textpdf, textFont.size) - textFont.getCalculatedBaseFont(true).getDescentPoint(textpdf, textFont.size)

                // Menghitung posisi teks di tengah dokumen secara horizontal dan vertikal
                val textX = (pageWidth - textWidth) / 2
                val textY = (pageHeight - textHeight) / 2 - 250

                // Mendapatkan PdfContentByte dari writer
                val canvas = writer.directContent

                // Mulai teks
                canvas.beginText()
                canvas.setFontAndSize(textFont.getCalculatedBaseFont(true), textFont.size)
                canvas.setColorFill(BaseColor.BLACK)

                // Menampilkan teks di tengah dokumen
                canvas.showTextAligned(Element.ALIGN_LEFT, textpdf, textX, textY, 0f)

                // Selesai teks
                canvas.endText()


                // Mulai teks
                canvas.beginText()
                canvas.setFontAndSize(textFont.getCalculatedBaseFont(true), textFont.size)
                canvas.setColorFill(BaseColor.BLACK)

                // Menampilkan teks di bawah gambar
                canvas.showTextAligned(Element.ALIGN_LEFT, textpdf, textX, textY, 0f)

                // Selesai teks
                canvas.endText()

                mDoc.close()



                Toast.makeText(this, "PDF Berhasil Dibuat", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun onShareButtonClick(view: View) {
        // Panggil fungsi savePDF untuk menyimpan PDF
        val uri = createPDFFileUri() // Ubah dengan kode yang menghasilkan URI PDF
        if (uri != null) {
            savePDF(uri)
        }

        // Panggil fungsi untuk membagikan PDF
        if (uri != null) {
            sharePDFViaWhatsApp(uri)
        }
    }

    private fun sharePDFViaWhatsApp(uri: Uri?) {

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "application/pdf"
        intent.putExtra(Intent.EXTRA_STREAM, uri)

        val shareIntent = Intent.createChooser(intent, "Bagikan PDF melalui...")
        shareIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(createWhatsAppIntent(uri), createBluetoothIntent(uri)))

        startActivity(shareIntent)
        finish()
    }

    private fun createWhatsAppIntent(uri: Uri?): Intent {
        val whatsappIntent = Intent(Intent.ACTION_SEND)
        whatsappIntent.type = "application/pdf"
        whatsappIntent.putExtra(Intent.EXTRA_STREAM, uri)
        whatsappIntent.setPackage("com.whatsapp")
        return whatsappIntent
    }

    private fun createBluetoothIntent(uri: Uri?): Intent {
        val bluetoothIntent = Intent(Intent.ACTION_SEND)
        bluetoothIntent.type = "application/pdf"
        bluetoothIntent.putExtra(Intent.EXTRA_STREAM, uri)
        bluetoothIntent.setPackage("com.android.bluetooth")
        return bluetoothIntent
    }

    private fun createPDFFileUri(): Uri? {
        val textPDF = binding?.tvCodeDesc?.text.toString()
        val pdfFileName = "NAVIKU_$textPDF.pdf"
        val pdfFile = File(getExternalFilesDir(null), pdfFileName)
        return FileProvider.getUriForFile(this, "$packageName.fileprovider", pdfFile)
    }

    private fun generateFileName(): String {
        val textPDF = binding?.tvCodeDesc?.text.toString()
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        return "NAVIKU_$textPDF.pdf"
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun obtainViewModel(activity: AppCompatActivity): PersonalCodeDetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[PersonalCodeDetailViewModel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityPersonalCodeDetailBinding = null
    }
}