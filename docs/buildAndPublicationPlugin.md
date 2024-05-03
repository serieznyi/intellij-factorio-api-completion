# Sign and publication plugin

## Sign

1) Prepare .env file with Intellij publication token and sign RSA keys
   ```
   # .env
   INTELLIJ_PLUGIN_CERTIFICATE_CHAIN_FILE_PATH=<absolute-path-to-chain-file>
   INTELLIJ_PLUGIN_PRIVATE_KEY_FILE_PATH=<absolute-path-to-key-file>
   INTELLIJ_PLUGIN_PRIVATE_KEY_PASSWORD=<key phrase>
   INTELLIJ_PLUGIN_PUBLISH_TOKEN=<publication-token>
   ```
   
2) Run building and sign plugin (Linux)
   ```shell
   export $(cat .env | xargs) && ./gradlew build signPlugin
   ```
   
   In `build/distributions/` dir will create archive with postfix `-signed.zip`

## Publish

```shell
export $(cat .env | xargs) && ./gradlew publishPlugin
```

## Other

 - [How crate keys for plugin signing](https://plugins.jetbrains.com/docs/intellij/plugin-signing.html)