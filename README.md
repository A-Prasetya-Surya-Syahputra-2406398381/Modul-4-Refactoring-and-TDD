1. TDD Flow Reflection (Percival, 2017)

    - flow yang dilakukan TDD dengan membuat test untuk method method lalu melihat test tersebut gagal lalu mengimplementasikan kode minimum agar testnya pass sangat melatih disiplin

    - flow tersebut memastikan setiap kode di `OrderServiceImpl` didorong oleh kebutuhan spesifik yang diuji. Contohnya metode `updateStatus` diimplementasikan secara khusus untuk menangani pembaruan status yang berhasil sekaligus melempar `NoSuchElementException` untuk ID yang tidak ditemukan, sesuai dengan ekspektasi dari tes

2. Reflection on F.I.R.S.T. Principles
   - Fast: penggunaan mockito untuk melakukan mocking pada `OrderRepository` menghindari operasi database yang lambat
   - Independent: Setiap mode pengujian menggunakan data yang diinisialisasi ulang dalam blok `@BeforeEach` yang memastikan bahwa suatu hasil test independen dari tes lainnya
   - Repeatable: Karena tidak ada dependency keluar, tes ini akan konsisten berhasil dimanapun dijalankan
   - Self-Validating: Tes menggunakan <i>assertions</i> yang jelas. Hasilnya hanya ada dua yaitu success atau fail tanpa perlu interpretasi manual pada log
   - Timely: Tes dibuat sebelum fungsionalitas diimplementasikan