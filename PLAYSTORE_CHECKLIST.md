# Play Store Launch Checklist (SnapNest)

## 1) Play Console
- Create the app entry in Play Console (name, language, free/paid)
- Complete **App content**:
  - App access
  - Ads declaration
  - Content rating
  - Target audience
  - Data safety
  - Privacy policy URL (you can host `PRIVACY_POLICY.md` using GitHub Pages)

## 2) GitHub Secrets (for signed release builds)
Add these in: **Repo → Settings → Secrets and variables → Actions**

- `KEYSTORE_BASE64` — base64 of your `upload-keystore.jks`
- `KEYSTORE_PASSWORD`
- `KEY_ALIAS`
- `KEY_PASSWORD`

Optional (for automated internal uploads):

- `PLAY_SERVICE_ACCOUNT_JSON`

## 3) Build the AAB
Run the GitHub Actions workflow: **Build Release AAB**.

Artifact output:

- `app-release-aab` → `app-release.aab`

## 4) First upload (manual)
In Play Console:

- **Testing → Internal testing → Create release**
- Upload the generated `app-release.aab`
- Add release notes
- Publish to internal testing

## 5) Production
Once internal testing is OK:

- **Production → Create release**
- Upload AAB (bump `versionCode` for every new build)
- Start rollout

## Notes
- `applicationId` is set to: `com.snehamarathe.snapnest` (do not change after publishing)
- This repo targets SDK 34.