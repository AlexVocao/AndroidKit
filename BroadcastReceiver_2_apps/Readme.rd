Note:
1. User defined action can be any value like - "ACTION_A", "ACTION_B"
2. Should set package that specific package the app wants to send broadcast to another app: A send to B then A should set intent.setPackage("package_name_B")
3. Listen: can configure by static in AndroidManifest.xml with tag <receiver></receiver>
4. Must have "export="true""
