import 'dart:async';
import 'dart:io';
import 'package:flutter/services.dart';

class UnusedAppRestrictions {
  static const MethodChannel _channel =
      MethodChannel('unused_app_restrictions');

  static Future<bool> getStatus() async {
    if (Platform.isAndroid) {
      final String status =
          await _channel.invokeMethod('getUnusedAppRestrictionsStatus');
      return status == 'ENABLED';
    } else {
      return false;
    }
  }
}
