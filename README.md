This library checks if "Pause app activity if unused" is turned on or off in the app settings which is only available on android.

#### reference
https://developer.android.com/topic/performance/app-hibernation

#### how to use
```
TextButton(
    onPressed: () async {
        final status =
            await UnusedAppRestrictions.getUnusedAppRestrictionsStatus();
        print(status); // ENABLED, DISABLED
    },
    child: const Text('getUnusedAppRestrictionsStatus'),
),