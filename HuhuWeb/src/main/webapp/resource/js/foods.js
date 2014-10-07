define("module/browser", [],
function() {
	return {
		versions: function() {
			var e = navigator.userAgent;
			return navigator.appVersion,
			{
				trident: e.indexOf("Trident") > -1,
				presto: e.indexOf("Presto") > -1,
				webKit: e.indexOf("AppleWebKit") > -1,
				gecko: e.indexOf("Gecko") > -1 && -1 == e.indexOf("KHTML"),
				mobile: !!e.match(/AppleWebKit.*Mobile.*/) || !!e.match(/AppleWebKit/),
				ios: !!e.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/),
				android: e.indexOf("Android") > -1 || e.indexOf("Linux") > -1,
				iPhone: e.indexOf("iPhone") > -1 || e.indexOf("Mac") > -1,
				iPad: e.indexOf("iPad") > -1,
				webApp: -1 == e.indexOf("Safari"),
				weixin: e.toLowerCase().indexOf("micromessenger") > -1,
				chrome: e.indexOf("Chrome") > -1 || e.indexOf("CriOS") > -1
			};
		} ()
	};
}),
define("module/TextMarquee", [],
function() {
	var e = Class.extend({
		_speed: 50,
		init: function(e, t) {
			null != t && (this._speed = null == t.speed ? this._speed: t.speed);
			var n = $(e),
			r = n.width(),
			i = r / this._speed,
			s = n.html();
			n.html(s + s);
			var o = "marque" + (new Date).valueOf(),
			u = "@-webkit-keyframes " + o + " { 0% {-webkit-transform:translate3d(0,0,0)} 100% {-webkit-transform:translate3d(-" + r + "px,0,0)}}\n";
			u += u.replace(/\-webkit\-/g, ""),
			$("head").append("<style>" + u + "</style>");
			var a = o + " " + i + "s linear infinite";
			n.css({
				"-webkit-animation": a,
				animation: a,
				width: 2 * r + "px"
			});
		}
	});
	return e;
}),
define("page/restaurant/foods/Cart", ["page/restaurant/foods/CartView"],
function(e) {
	var t = function(t) {
		this._options = t || {},
		this._view = null,
		this._orders = {},
		this._orderNum = 0,
		this._totalPrice = 0,
		this.minimum = this._options.minPrice || 0,
		this._view = new e(this);
	};
	return t.prototype = {
		_addItem: function(e, t) {
			var n = parseInt(t.num),
			r = parseFloat(t.price);
			n > 0 && (this._orders[e] == null ? this._orders[e] = {
				price: r,
				num: n
			}: this._orders[e].num += n, this._orderNum += n, this._totalPrice += r * n)
		},
		add: function(e, t, n) {
			t.num = n || 1,
			this._addItem(e, t),
			this._view.updateView(this)
		},
		remove: function(e, t) {
			this._orders[e] != null && this._orders[e].num > 0 && (t = t || 1, t = Math.min(this._orders[e].num, t), this._orders[e].num -= t, this._orderNum -= t, this._totalPrice -= this._orders[e].price * t, this._orders[e].num <= 0 && delete this._orders[e], this._view.updateView(this))
		},
		getItemNum: function(e) {
			return this._orders[e] == null ? 0 : this._orders[e].num;
		},
		getResidual: function() {
			var e = Math.round((this.minimum - this._totalPrice) * 100) / 100;
			return e > 0 ? e: 0;
		},
		getTotalPrice: function() {
			return Math.round(this._totalPrice * 100) / 100
		},
		getOrderNum: function() {
			return this._orderNum;
		},
		getOrders: function() {
			return this._orders;
		},
		updateOrders: function(e) {
			for (var t in e) this._addItem(t, e[t]);
			this._view.updateView(this);
		}
	},
	t;
}),
define("page/restaurant/foods/CartView",
function() {
	var e = function() {
		this._jQcontainer = $("#cart"),
		this._jQpriceWrap = this._jQcontainer.find(".j-cart-price"),
		this._jQDetailWarp = $("#showDetail"),
		this._jQnumWrap = this._jQcontainer.find(".j-cart-num"),
		this._jQbtnConfirm = this._jQcontainer.find(".j-cart-btn-confirm"),
		this._jQbtnUnavail = this._jQcontainer.find(".j-cart-btn-unavail");
	};
	return e.prototype = {
			updateView: function(e) {
				var t = e.getOrderNum(),
				n = e.getTotalPrice(),
				r = e.getResidual();
				this._jQpriceWrap.text("￥" + n),
				this._jQnumWrap.text(t),
				t == 0 ? this._jQnumWrap.hide() : this._jQnumWrap.show()
				
			}
		},
	e;
}),
define("page/restaurant/foods/CookieOp",
function() {
	var e = function() {
		this.orders = {},
		this.poi = null,
		this._readFromCookie();
	};
	return e.prototype = {
		saveToCookie: function(e, t) {
			var n = [];
			for (var r in t) {
				var i = parseInt(t[r].num);
				i > 0 && (n.push(r), n.push(","), n.push(i), n.push("|"));
			}
			var s = e + ":" + n.join("");
			$.cookie("w_c", "", {
				path: "/order",
				expires: -1
			}),
			$.cookie("w_c", "", {
				path: "/order/preview",
				expires: -1
			}),
			$.cookie("w_c", "", {
				path: "/order/confirm",
				expires: -1
			}),
			$.cookie("w_c", s, {
				path: "/"
			});
		},
		_readFromCookie: function() {
			var e = null;
			if (e != null) {
				var t = e.indexOf(":"),
				n = e.substring(0, t),
				r = e.substring(t + 1);
				n = parseInt(n);
				if (n >= 0) {
					var i = r.split("|");
					for (var s = 0,
					o = i.length; s < o; s++) {
						var u = i[s].split(",");
						if (u.length == 2) {
							var a = parseInt(u[1]),
							f = parseInt(u[0]);
							a > 0 && f > 0 && (this.orders[f] = {
								num: a
							});
						}
					}
					this.poi = n;
				}
			}
		}
	},
	e;
}),
define("page/restaurant/foods", ["page/restaurant/foods/Cart", "page/restaurant/foods/CookieOp", "module/browser", "module/TextMarquee"],
function(e, t, n, r) {
	return {
		_cart: null,
		_cookieObj: null,
		_options: {},
		_foodTagMap: {},
		_scrollMain: null,
		_scrollAside: null,
		init: function(n) {
			FastClick.attach(document.body);
			var r = this;
			this._cart = new e({
				minPrice: n.minPrice
			}),
			this._cookieObj = new t,
			this._options = $.extend(this._options, n);
			if (this._cookieObj.poi == this._options["poi"]) {
				var i = this._cookieObj.orders,
				s = {};
				for (var o in i) {
					var u = $("#food" + o);
					if (u.size() > 0) {
						s[o] = i[o],
						s[o].price = u.data("price");
						if (s[o].num > 0) {
							u.find(".j-item-num").text(s[o].num).show(),
							u.find(".j-remove-item").show();
							console.log(o);
							var a = this._getTagIdByFoodId(o);
							this._updateTagChosenNum(a, s[o].num);
						}
					}
				}
				this._cart.updateOrders(s);
			}
			$("#cart").find(".j-cart-btn-confirm").click(function() {
				var e = r._cart.getOrders();
				r._cookieObj.saveToCookie(r._options.poi, e),
				delayGo("/order/preview");
			}),
			$("#detail-link").click(function() {
				return $.post("/logRestaurantToDetailClick"),
				delayGo(this.href),
				!1;
			}),
			this._initFoodList(),
			this._initBulletin(),
			window.bLog("waimai_i_foods");
		},
		_updatePosition: function() {
			$("#menuwrap").css("top", $("#top-wrap").height() + $("#header").height() + "px");
		},
		_initFoodList: function() {
			var e = this,
			t = $("#asidewrap").find(".j-tag"),
			r = t.filter(".focus");
			var foods = {}; 
			this._initFoodListPosition(),
			this._initScrollRegion();
			var i = n.versions.chrome ? "tap": "click";
			$("#mainwrap").delegate(".j-add-item", i,
			function() {
				var t = $(this).parent(".j-item-console");
				foodId = t.parent().parent().attr('id');
				n = t.data("price"),
				x = t.data("serial"),
				i = t.data("minbuy"),
				s = e._cart.getItemNum(x),
				o = s == 0 && i > 0 ? i: 1;
				e._cart.add(x, {
					price: n
				},
				o),
				s += o;
				foods[x] = {'fooID':x,'foodNum':s,'foodPrice':n};
				e._updateFoodOrder(foods);
				var u = t.find(".j-item-num");
				u.text(s),
				s > 0 && (u.show(), t.find(".j-remove-item").show());
				var a = e._getTagIdByFoodId(x);
				e._updateTagChosenNum(a, o);
			}),
			$("#mainwrap").delegate(".j-remove-item", i,
			function() {
				var f = e._cookieObj.orders;
				for(var o in f) {
					var u = $("#food" + o);
				}
				var t = $(this).parent(".j-item-console"),
				p = t.data("price"),
				n = t.data("serial"),
				x = t.data("minbuy"),
				i = e._cart.getItemNum(n),
				s = i,
				o = i <= x ? i: 1;
				e._cart.remove(n, o),
				i -= o;
				var u = t.find(".j-item-num");
				u.text(i),
				i <= 0 && (u.text(0).hide(), t.find(".j-remove-item").hide());
				var a = t.parents(".j-foodlist").attr("id").replace("foodlist", "");
				s != 0 && e._updateTagChosenNum(a, -o);
				foods[n] = {'foodID' : n,'foodNum':s-1,'foodPrice':p};
				e._updateFoodOrder(foods);
			}),
			$("#asidewrap").delegate(".j-tag", i,
			function() {
				var t = r.data("key");
				r.removeClass("focus"),
				$("#foodlist" + t).hide(),
				r = $(this),
				r.addClass("focus"),
				t = r.data("key"),
				$("#foodlist" + t).show(),
				e._scrollMain.scrollTo(0, 0, 0),
				e._scrollMain.refresh(),
				$("#mainwrap").trigger("scroll:loadpic");
			}),
			$("#mainwrap").find(".j-food-pic").lazyload({
				data_attribute: "src-retina",
				container: "#menuwrap",
				event: "scroll:loadpic",
				load: function() {
					var e = $(this);
					e.attr("visibility", "hidden");
					var t = e.width(),
					n = e.height();
					e.removeAttr("width"),
					e.removeAttr("height");
					var r = e.width(),
					i = e.height(),
					s = 0,
					o = 0;
					r > i ? (r = n * r / i, i = n, s = (t - r) / 2) : (i = t * i / r, r = t, o = (n - i) / 2),
					e.css({
						width: r + "px",
						height: i + "px",
						"margin-left": s,
						"margin-top": o,
						visibility: "visible"
					})
				}
			})
		},
		_initScrollRegion: function() {
			function s(s) {
				s.y < -150 && !r && (i = $("#top-wrap").height(), i > 0 && (e.css({
					"-webkit-transform": "translate3d(0,-" + i + "px,0)",
					transform: "translate3d(0,-" + i + "px,0)",
					"-webkit-transition": "-webkit-transform 0.6s",
					transition: "transform 0.6s",
					height: e.height() + i + "px"
				}), r = !0, setTimeout(function() {
					t.refresh(),
					n.refresh()
				},
				620)))
			}
			function o(i) {
				i.y > -2 && r && (e.css({
					"-webkit-transform": "translate3d(0,0,0)",
					transform: "translate3d(0,0,0)",
					"-webkit-transition": "-webkit-transform 0.6s",
					transition: "transform 0.6s",
					height: "auto"
				}), r = !1, setTimeout(function() {
					t.refresh(),
					n.refresh()
				},
				620))
			}
			var e = $("#menuwrap"),
			t = this._scrollAside = new IScroll("#asidewrap", {
				tap: !0
			}),
			n = this._scrollMain = new IScroll("#mainwrap", {
				tap: !0
			}),
			r = !1,
			i;
			n.on("scrollEnd",
			function() {
				s(this),
				o(this),
				$("#menuwrap").trigger("scroll:loadpic")
			}),
			t.on("scrollEnd",
			function() {
				o(this);
			});
		},
		_initFoodListPosition: function() {
			var e = $("#menuwrap"),
			t = e.offset().top;
			e.css({
				position: "absolute",
				top: t,
				bottom: 0,
				height: "auto"
			});
		},
		_updateTagChosenNum: function(e, t) {
			
			var n = $("#tag" + e);
			r = n.find(".j-num");
			r.size() == 0 && t > 0 ? (r = $('<div class="j-num tag-num">' + t + "</div>"), r.appendTo(n)) : r.size() > 0 && (t = parseInt(r.text()) + t, t > 0 ? r.text(t) : r.remove());
		},
		_getTagIdByFoodId: function(e) {
			var t;
			var foodId = this._foodTagMap[e] ? t = this._foodTagMap[e] : (t = $("#food" + e).parents(".j-foodlist").attr("id").replace("foodlist", ""), this._foodTagMap[e] = t);
			
			return  foodId,
			t;
		},
		
		_updateFoodOrder: function(foods) {
			
			$('#showDetail tbody').empty();
			var totalPrice = 0;
	        $.each(foods,function(name,value) {
	        	var foodName = $('#food'+name).find('.j-foodname').text();
	            var foodNum = value['foodNum'];
	            if( foodNum == 0) {
	            	return true;
	            }
	            var foodId = name;
	            var oneFoodTotalPrice = value['foodPrice']*foodNum;
	            var addContact = '<tr>' +
	            				'<td>' + foodName +'</td>' +
	            				'<td>' + foodNum +'</td>' +
	            				'<td>' + oneFoodTotalPrice +'</td>' +
	            				'</tr>';
	            totalPrice += oneFoodTotalPrice;
	            $('#showDetail tbody').append(addContact);
	        });
            var TotalPriceContact = '<tr>' +
			'<td>' + '总价' +'</td>' +
			'<td>' +  '' +'</td>' +
			'<td>' + totalPrice +'</td>' +
			'</tr>';

	        $('#showDetail tbody').append(TotalPriceContact);
	        var data = new Array();;
	        $.each(foods,function(key , value){
	            var foodNum = value['foodNum'];
	        	if( foodNum == 0) {
	            	return true;
	            }
	        	data.push(value);
	        });
	        $('#foodJson').attr('data',JSON.stringify(data));
		},
		
		_removeFoodOrder: function(foods) {
	        $.each(foods,function(name,value) {
	        	var foodName = $('#'+name).find('.j-foodname').text();
	            var foodNum = value;
	            var foodId = name + '_id';
	            $('#showDetail').find('tbody').append('<span id="'+ foodId +'">' + foodName + '   x' + foodNum +'</span>' +'</br>');
	        });
		},
		
		
		
		_getFoodId: function(e) {
			var t;
			return this._foodTagMap[e] ? t = this._foodTagMap[e] : (t = $("#food" + e), this._foodTagMap[e] = t),
			t;
		},
		
		_getFoodJson: function(e) {
			var t =  JSON.stringify(e);
			return 342;
		},
		
		
		_initBulletin: function() {
			var e = this;
			$("#bulletin-bar").find(".j-bulletin-close").click(function() {
				$("#bulletin-bar").hide(),
				e._updatePosition();
			});
			var t = $("#bulletin-bar").find(".j-bulletin-word"),
			n = t.find(".j-bulletin-word-inner");
			t.width() < n.width() && new r(n),
			t.click(function() {
				return $.post("/logRestaurantAnnouncementClick"),
				delayGo(this.href),
				!1;
			});
		}
	};
});