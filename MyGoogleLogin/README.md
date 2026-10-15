# My Google Login (Android)

Aplikasi demo login & daftar dengan akun Google.

## Fitur
- Tombol "Sign in with Google" resmi
- Popup otomatis menampilkan semua akun Google di perangkat
- Login = daftar otomatis
- Logout
- GitHub Actions auto-build APK

## Setup

### 1. Google Cloud Console
1. Buat project di https://console.cloud.google.com
2. Aktifkan **OAuth consent screen**
3. Buat **OAuth Client ID**:
   - Tipe **Android** → isi package `com.example.mygooglelogin` + SHA-1
   - Tipe **Web application** → copy Client ID-nya

### 2. Ambil SHA-1
```bash
keytool -list -v -keystore ~/.android/debug.keystore \
  -alias androiddebugkey -storepass android -keypass android
```

### 3. Konfigurasi
Buka `app/src/main/java/com/example/mygooglelogin/LoginActivity.java`
Ganti `YOUR_WEB_CLIENT_ID` dengan Web Client ID Anda.

### 4. Build
Sync Gradle → Run.

## Download APK dari GitHub Actions
1. Push ke GitHub
2. Buka tab **Actions** di repo
3. Klik workflow run terbaru → **Artifacts** → download `app-debug`

## Push ke GitHub

```bash
git init
git add .
git commit -m "init: Google Sign-In demo"
git branch -M main
git remote add origin https://github.com/USERNAME/REPO.git
git push -u origin main
```

## Lisensi
MIT
