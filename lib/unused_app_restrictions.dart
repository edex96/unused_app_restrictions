import 'dart:async';
import 'package:flutter/services.dart';

class UnusedAppRestrictions {
  static const MethodChannel _channel =
      MethodChannel('unused_app_restrictions');

  static Future<bool> getStatus() async {
    final String status =
        await _channel.invokeMethod('getUnusedAppRestrictionsStatus');
    return status == 'ENABLED';
  }
}
