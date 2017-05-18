var common = {
    isCancel: false,
    isSure: false,
    show_pop: function (node, content) {//显示
      node.find('.pop_content').html(content);
      node.css('display', 'block');
    },
    pop_cancel: function (fn) {//取消事件
      fn && fn();
      $('#pop_confirm').css('display', 'none');
    },
    pop_sure: function (fn) {//确认事件
      fn && fn();
      $('#pop_confirm').css('display', 'none');
    },
    //当时间是个位数时，在前面加0
    add0: function (m) {
      return m < 10 ? '0' + m : m;
    },
    /**
     * @description 得到当地时间
     * @param {Object} nS
     */
    getLocalTime: function (nS) {
      var time = new Date(nS);
      var y = time.getFullYear();
      var m = time.getMonth() + 1;
      var d = time.getDate();
      var h = time.getHours();
      var mm = time.getMinutes();
      var s = time.getSeconds();
      return y + '-' + this.add0(m) + '-' + this.add0(d) + ' ' + this.add0(h) + ':' + this.add0(mm) + ':' + this.add0(s);

    },
    //对象序列化
    formatParams: function (data) {
      var arr = [];
      for (var name in data) {
        arr.push(encodeURIComponent(name) + "=" + encodeURIComponent(data[name]));
      }
      arr.push(("v=" + Math.random()).replace(".", ""));
      return arr.join("&");
    },
    //警告框
    warning: function (content, time) {
      var t = time ? time : 2000;
      $('#pop_warn_content').html(content);
      $('#pop_warn').show();
      setTimeout(function () {
        $('#pop_warn').hide();
      }, t);
    },
    success: function (obj) {
      var t = obj.time ? obj.time : 2000;
      $('#pop_success_content').html(obj.content);
      $('#pop_success').show();
      setTimeout(function () {
        obj.fn && obj.fn();
        $('#pop_success').hide();
      }, t);
    },
    go: function (url) {
      location.href = url;
    },
    postForm: function (Option) {
      $.ajax({
        type: 'post',
        data: Option.data,
        url: Option.url,
        success: function (json) {
          if (json.ok) {
            if (Option.success) {
              Option.success(json);
            }
          } else if (json.code == 400) {
            common.warning(json.message);
          } else {
            if (Option.failure) {
              Option.failure(json);
            }
          }
        },
        error: function (error) {
          common.warning(error.errorMsg);
        }
      })
    },
    postJson: function (Option) {
      $.ajax({
        type: 'post',
        data: JSON.stringify(Option.data),
        url: Option.url,
        contentType: 'application/json;charset=utf-8',
        success: function (json) {
          if (json.ok) {
            if (Option.success) {
              Option.success(json);
            }
          } else if (json.code == 400) {
            common.warning(json.message);
          } else {
            if (Option.failure) {
              Option.failure(json);
            }
          }
        },
        error: function (error) {
          common.warning(error.errorMsg);
        }
      })
    },
    get: function (Option) {
      $.ajax({
        type: 'get',
        data: Option.data,
        url: Option.url,
        success: function (json) {
          if (json.ok) {
            if (Option.success) {
              Option.success(json);
            }
          } else if (json.code == 400) {
            common.warning(json.message);
          } else {
            if (Option.failure) {
              Option.failure(json);
            }
          }
        },
        error: function (error) {
          common.warning(error.errorMsg);
        }
      })
    },
    formatDate: function (date, format) {
      if (!date) return;
      date = date.replace("T", " ");
      if (!format) format = "yyyy-MM-dd";
      date = new Date(date);
      if (!date instanceof Date) return;
      var dict = {
        "yyyy": date.getFullYear(),
        "M": date.getMonth() + 1,
        "d": date.getDate(),
        "H": date.getHours(),
        "m": date.getMinutes(),
        "s": date.getSeconds(),
        "MM": ("" + (date.getMonth() + 101)).substr(1),
        "dd": ("" + (date.getDate() + 100)).substr(1),
        "HH": ("" + (date.getHours() + 100)).substr(1),
        "mm": ("" + (date.getMinutes() + 100)).substr(1),
        "ss": ("" + (date.getSeconds() + 100)).substr(1)
      };
      return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g, function () {
        return dict[arguments[0]];
      });
    },
    removeHtmlLabel:function(node){
         return node.text().trim()? node.html().trim().replace(new RegExp(/\\u003c/g), "<").replace(new RegExp(/\\u003e/g), ">").replace(new RegExp(/<[^div|p|br][^/div][^>]+>/g),"").replace(new RegExp(/<div[^>]*>/g), "<div>").replace(new RegExp(/<p[^>]*>/g), "<p>").replace(new RegExp(/[&nbsp;]*(&nbsp;)$/g), '').replace(new RegExp(/^(&nbsp;)(&nbsp;)*/g), '').trim().replace(/^(&nbsp; )*/g,'').replace(/(&nbsp; )*$/g,'') :'';
    },
    ajax_error:function(error,v){
    		var msg = error.errorMsg || '系统出错';
			common.warning(msg);
			if(v){
				v = false;
			}
    },

  autoTextarea: function (elem, extra, maxHeight) {
    var isFirefox;
      extra = extra || 0;
      isFirefox = !!document.getBoxObjectFor || 'mozInnerScreenX' in window,
              isOpera = !!window.opera && !!window.opera.toString().indexOf('Opera'),
              addEvent = function (type, callback) {
                elem.addEventListener ?
                        elem.addEventListener(type, callback, false) :
                        elem.attachEvent('on' + type, callback);
              },
              getStyle = elem.currentStyle ? function (name) {
                var val = elem.currentStyle[name];

                if (name === 'height' && val.search(/px/i) !== 1) {
                  var rect = elem.getBoundingClientRect();
                  return rect.bottom - rect.top -
                          parseFloat(getStyle('paddingTop')) -
                          parseFloat(getStyle('paddingBottom')) + 'px';
                }
                ;

                return val;
              } : function (name) {
                return getComputedStyle(elem, null)[name];
              },
              minHeight = parseFloat(getStyle('height'));

      elem.style.resize = 'none';

      var change = function () {
        var scrollTop, height,
                padding = 0,
                style = elem.style;

        if (elem._length === elem.value.length) return;
        elem._length = elem.value.length;

        if (!isFirefox && !isOpera) {
          padding = parseInt(getStyle('paddingTop')) + parseInt(getStyle('paddingBottom'));
        }
        ;
        scrollTop = document.body.scrollTop || document.documentElement.scrollTop;

        elem.style.height = minHeight + 'px';
        if (elem.scrollHeight > minHeight) {
          if (maxHeight && elem.scrollHeight > maxHeight) {
            height = maxHeight - padding;
            style.overflowY = 'auto';
          } else {
            height = elem.scrollHeight - padding;
            style.overflowY = 'hidden';
          }
          ;
          style.height = height + extra + 'px';
          scrollTop += parseInt(style.height) - elem.currHeight;
          document.body.scrollTop = scrollTop;
          document.documentElement.scrollTop = scrollTop;
          elem.currHeight = parseInt(style.height);
        }
        ;
      };
      addEvent('propertychange', change);
      addEvent('input', change);
      addEvent('focus', change);
      change();
    }
  }