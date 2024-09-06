# ASMARA - Aspirasi Masyarakat

ASMARA adalah aplikasi Android berbasis **Kotlin** yang memungkinkan masyarakat menyampaikan aspirasi, saran, dan keluhan secara langsung kepada pihak berwenang. Aplikasi ini bertujuan untuk meningkatkan komunikasi antara masyarakat dan pemerintah dalam hal pelayanan publik dan kebijakan.

---

## Persyaratan Sistem:
- **Android Studio** versi terbaru
- **Gradle** versi terbaru
- **Kotlin** plugin terbaru
- Android SDK minimal versi 21 (Android 5.0 Lollipop)

---

## Cara Pemasangan (Installation)

### 1. Clone Repository
Clone project ini dari repository ke lokal mesin kamu dengan perintah berikut:
```bash
[git clone https://github.com/username/asmara-app.git](https://github.com/kevinalfito69/ASMARA-Android.git)
```

### 2. Buka Project di Android Studio
1. Buka Android Studio.
2. Pilih **File > Open**.
3. Arahkan ke direktori project hasil clone dan pilih folder tersebut.
4. Klik **OK** untuk memuat project.

### 3. Atur Konfigurasi Build
1. Buka file `build.gradle` pada level project dan modul.
2. Pastikan kamu telah menambahkan dependensi yang dibutuhkan:
   ```gradle
   dependencies {
       implementation 'com.squareup.retrofit2:retrofit:2.9.0'
       implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1'
   }
   ```
3. Sinkronkan Gradle dengan menekan tombol **Sync Now** di bagian atas Android Studio.

### 4. API Configuration
Aplikasi ini menggunakan **API REST** untuk pengelolaan data aspirasi. Pastikan untuk menambahkan URL API pada file konfigurasi.

1. Buka file `ApiService.kt`.
2. Ubah `BASE_URL` menjadi URL API yang sesuai:
   ```kotlin
   const val BASE_URL = "https://your-api-url.com/"
   ```

### 5. Jalankan Aplikasi
1. Pilih emulator atau perangkat fisik yang terhubung.
2. Klik tombol **Run** (ikon segitiga hijau) di toolbar atau tekan **Shift + F10** untuk menjalankan aplikasi.

### 6. Debugging dan Testing
- Gunakan **Logcat** untuk memantau log saat menjalankan aplikasi.
- Pastikan API dan fungsionalitas berjalan dengan baik dengan mencoba mengirim aspirasi dari aplikasi.
