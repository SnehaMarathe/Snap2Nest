# SnapNest — Auto Collage

## UX
- Home screen: New Collage + Recent Exports + Templates
- Editor screen: Collage canvas + bottom tabs (Templates / Adjust / Background / Export)
- Slot tap shows an action sheet: Camera / Gallery / Remove
- In-slot CameraX with grid + flash + switch camera
- Per-slot draft capture persistence
- Crop (uCrop) then pinch/drag positioning
- Export renders one final bitmap and saves to `Pictures/AutoCollage`

## Build (GitHub Actions)

This repo is set up to build a **signed Release AAB** via GitHub Actions.

### 1) Build a release AAB artifact
Run the **Build Release AAB** workflow (or push to `main`). It will produce:

- `app/build/outputs/bundle/release/app-release.aab`

### 2) Signing (required for Play uploads)
Add these GitHub Actions secrets:

- `KEYSTORE_BASE64` — base64 of `upload-keystore.jks`
- `KEYSTORE_PASSWORD`
- `KEY_ALIAS`
- `KEY_PASSWORD`

The workflow decodes the keystore to `app/upload-keystore.jks` and builds a signed `bundleRelease`.

### 3) Optional: upload to Play Internal track
There is an optional workflow: **Publish to Play (Internal)**.

Additional required secret:

- `PLAY_SERVICE_ACCOUNT_JSON` — the full JSON of your Play service account key

Note: Play requires you to create the app in Play Console at least once before API uploads.
