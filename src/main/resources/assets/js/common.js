var Common = {
  get: function (Option) {
    $.ajax({
      type: 'GET',
      url: Option.url,
      data: Option.params,
      contentType: 'application/json;charset=utf-8',
      success: function(json) {
        if (json.ok) {
          if ($.isFunction(Option.ok)) {
            Option.ok(json);
          } else {
            App.$message({showClose: true, message: json.message, type: 'success'});
          }
        } else {
          if ($.isFunction(Option.err)) {
            Option.err(json);
          } else {
            App.$message({showClose: true, message: json.message, type: 'error'});
          }
        }
      }
    });
  },
  postJson: function (Option) {
    $.ajax({
      type: 'POST',
      url: Option.url,
      data: JSON.stringify(Option.params),
      contentType: 'application/json;charset=utf-8',
      success: function(json) {
        if (json.ok) {
          if ($.isFunction(Option.ok)) {
            Option.ok(json);
          } else {
            App.$message({showClose: true, message: json.message, type: 'success'});
          }
        } else {
          if ($.isFunction(Option.err)) {
            Option.err(json);
          } else {
            App.$message({showClose: true, message: json.message, type: 'error'});
          }
        }
      }
    });
  },
  postForm: function (Option) {
    $.ajax({
      type: 'POST',
      url: Option.url,
      data: Option.params,
      contentType: 'application/x-www-form-urlencoded;charset=utf-8',
      success: function(json) {
        if (json.ok) {
          if ($.isFunction(Option.ok)) {
            Option.ok(json);
          } else {
            App.$message({showClose: true, message: json.message, type: 'success'});
          }
        } else {
          if ($.isFunction(Option.err)) {
            Option.err(json);
          } else {
            App.$message({showClose: true, message: json.message, type: 'error'});
          }
        }
      }
    });
  }
};