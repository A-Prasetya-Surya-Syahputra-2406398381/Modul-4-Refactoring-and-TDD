### Refleksi

Prinsip yang diterapkan

Single Responsibility Principle (SRP)

Setiap kelas memiliki satu tujuannya sendiri, Contohnya kelas kelas repositori hanya bertanggung jawab untuk persistence logic, dan kelas servis menghandle logika bisnis, Controller menghandle HTTP request dan routing.

Open-Closed Principle (OCP)

Service menggunakan interface untuk mendefinisikan behavior. Ini memungkinkan aplikasi untuk diextend dengan implementasi baru.

Liskov Substitution Principle (LSP)

Tidak ada kelas yang melanggar

Interface Segregation Principle (ISP)

Projek ini menggunakan interface spesifik untuk domain yang spesifik. CarService menyediakan method yang unik terhadap manajemen mobil sedangkan ProductService menghandle aksi umum produk. Client yang berinteraksi dengan Car tidak akan dipaksa untuk mengimplementasi method dari productService

Dependency Inversion Principle (DIP)

Controller tingkat tinggi tidak depends on low-level-concrete implementations. Contohnya CarController inject CarService (interface) daripada implementasinya

2. Advantages of the Current State

   Testability: Karena CarController sekarang bergantung pada Abstraksi (CarService) dan bukan implementasi konkret, Kita dapat melakukan Unit Testing dengan sangat mudah menggunakan Mock tanpa menyentuh data layer yang asli.

   Maintainability: Karena setiap kelas memiliki satu tanggung jawab, maka debugging akan jauh lebih cepat. Jika suatu fungsi berkaitan dengan penyimpanan mobil rusak, kita tahu pasti error pasti terjadi di CarRepository


