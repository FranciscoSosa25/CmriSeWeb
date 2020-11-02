/** 
 * PrimeFaces Apollo Layout
 */
PrimeFaces.widget.Apollo = PrimeFaces.widget.BaseWidget.extend({
    
    init: function(cfg) {
        this._super(cfg);
        this.wrapper = $(document.body).children('.layout-wrapper');
        this.topbar = this.wrapper.children('.topbar');
        this.menuContainer = this.wrapper.children('.layout-menu-container');
        this.menu = this.jq;
        this.menuWrapper = this.menu.closest('.layout-menu');
        this.menulinks = this.menu.find('a');
        this.expandedMenuitems = this.expandedMenuitems||[];
        this.profileButton = this.topbar.children('.profile');
        this.topbarMenu =  this.topbar.children('.topbar-menu');
        this.topbarLinks = this.topbarMenu.find('a');
        this.menuButton = $('#menu-button');
        this.menuActive = false;
        this.topbarMenuClick = false;
        this.topbarMenuButtonClick = false;
        this.nano = this.menuContainer.find('.nano');

        this._bindEvents();
        
        if(!this.isHorizontal() && !this.isSlim()) {
            this.restoreMenuState();
        }
        
        this.nano.nanoScroller({flash:true});
        this.nano.children('.nano-content').removeAttr('tabindex');
    },

    _bindEvents: function() {
        var $this = this;
        
        this.menu.on('click', function(e) {  
            $this.menuClick = true;
        });
        
        this.menuButton.on('click', function(e) {  
            $this.menuButtonClick = true;
            
            if($this.isDesktop()) {
                if($this.isOverlay()) 
                    $this.wrapper.toggleClass('layout-overlay-active');
                else if($this.isStatic()) 
                    $this.wrapper.toggleClass('layout-static-inactive');
            }
            else {
                $this.wrapper.toggleClass('layout-mobile-active');
            }
                          
            e.preventDefault();
        });
        
        this.profileButton.on('click', function(e) {
            $this.topbarMenuButtonClick = true;
            $this.topbarMenu.toggleClass('topbar-menu-visible');
                        
            e.preventDefault();
        });
        
        this.menulinks.off('click').on('click', function(e) {
            var link = $(this),
            item = link.parent(),
            submenu = item.children('ul'),
            horizontal = $this.isHorizontal(),
            slim = $this.isSlim();
                                                 
            if(item.hasClass('active-menuitem')) {
                if(submenu.length) {
                    $this.removeMenuitem(item.attr('id'));
                    item.removeClass('active-menuitem');
                    
                    if(horizontal || slim) {
                        if(item.parent().is($this.jq)) {
                            $this.menuActive = false;
                        }
                        
                        submenu.hide();
                    }
                    else {
                        submenu.slideUp();
                    }
                }
            }
            else {
                $this.addMenuitem(item.attr('id'));
                
                if(horizontal || slim) {
                    $this.deactivateItems(item.siblings());
                    item.addClass('active-menuitem');
                    $this.menuActive = true;
                    submenu.show();
                }
                else {
                    $this.deactivateItems(item.siblings(), true);
                    $this.activate(item);
                }
            }
            
            if(!horizontal) {
                setTimeout(function() {
                    $(".nano").nanoScroller();
                }, 500);
            }
                                    
            if(submenu.length) {
                e.preventDefault();
            }
        });
        
        this.topbarLinks.on('click', function(e) {
            var link = $(this),
            item = link.parent(),
            submenu = link.next();
            
            item.siblings('.menuitem-active').removeClass('menuitem-active');
            
            if(item.hasClass('menuitem-active')) {
                item.removeClass('menuitem-active');
                link.next('ul').slideUp();
            }
            else {
                item.addClass('menuitem-active');
                link.next('ul').slideDown();
            }
            
            if(submenu.length) {
                e.preventDefault();   
            }
        });
        
        this.topbarMenu.on('click', function() {
            $this.topbarMenuClick  = true;
        });
        
        this.menu.find('> li').on('mouseenter', function(e) {    
            if(($this.isHorizontal() || $this.isSlim()) && $this.isDesktop()) {
                var item = $(this),
                link = item.children('a'),
                submenu = item.children('ul');
                
                if(!item.hasClass('active-menuitem')) {
                    $this.menu.find('.active-menuitem').removeClass('active-menuitem');
                    $this.menu.find('ul:visible').hide();
                    $this.menu.find('.ink').remove();
                    
                    if($this.menuActive) {
                        item.addClass('active-menuitem');
                        item.children('ul').show();
                    }
                }
            }
        });
                                
        $(document.body).on('click', function() {
            if(($this.isHorizontal() || $this.isSlim()) && !$this.menuClick && $this.isDesktop()) {
                $this.menu.find('.active-menuitem').removeClass('active-menuitem');
                $this.menu.find('ul:visible').hide();
                $this.menuActive = false;
            }
            
            if(!$this.topbarMenuClick && !$this.topbarMenuButtonClick) {
                $this.topbarMenu.removeClass('topbar-menu-visible');
            }
            
            if(!$this.menuButtonClick && !$this.menuClick) {
                $this.wrapper.removeClass('layout-overlay-active layout-mobile-active');
            }
            
            $this.menuButtonClick = false;
            $this.menuClick = false;
            $this.topbarMenuClick = false;
            $this.topbarMenuButtonClick = false;
        });
    },
         
    activate: function(item) {
        var submenu = item.children('ul');
        item.addClass('active-menuitem');

        if(submenu.length) {
            submenu.slideDown();
        }
    },

    deactivate: function(item) {
        var submenu = item.children('ul');
        item.removeClass('active-menuitem');
        
        if(submenu.length) {
            submenu.hide();
        }
    },
        
    deactivateItems: function(items, animate) {
        var $this = this;
        
        for(var i = 0; i < items.length; i++) {
            var item = items.eq(i),
            submenu = item.children('ul');
            
            if(submenu.length) {
                if(item.hasClass('active-menuitem')) {
                    var activeSubItems = item.find('.active-menuitem');
                    item.removeClass('active-menuitem');
                    item.find('.ink').remove();
                    
                    if(animate) {
                        submenu.slideUp('normal', function() {
                            $(this).parent().find('.active-menuitem').each(function() {
                                $this.deactivate($(this));
                            });
                        });
                    }
                    else {
                        submenu.hide();
                        item.find('.active-menuitem').each(function() {
                            $this.deactivate($(this));
                        });
                    }
                    
                    $this.removeMenuitem(item.attr('id'));
                    activeSubItems.each(function() {
                        $this.removeMenuitem($(this).attr('id'));
                    });
                }
                else {
                    item.find('.active-menuitem').each(function() {
                        var subItem = $(this);
                        $this.deactivate(subItem);
                        $this.removeMenuitem(subItem.attr('id'));
                    });
                }
            }
            else if(item.hasClass('active-menuitem')) {
                $this.deactivate(item);
                $this.removeMenuitem(item.attr('id'));
            }
        }
    },
            
    removeMenuitem: function (id) {
        this.expandedMenuitems = $.grep(this.expandedMenuitems, function (value) {
            return value !== id;
        });
        this.saveMenuState();
    },

    addMenuitem: function (id) {
        if ($.inArray(id, this.expandedMenuitems) === -1) {
            this.expandedMenuitems.push(id);
        }
        this.saveMenuState();
    },

    saveMenuState: function() {
        $.cookie('poseidon_expandeditems', this.expandedMenuitems.join(','), {path: '/'});
    },

    clearMenuState: function() {
        $.removeCookie('poseidon_expandeditems', {path: '/'});
    },

    setInlineProfileState: function(expanded) {
        if(expanded)
            $.cookie('poseidon_inlineprofile_expanded', "1", {path: '/'});
        else
            $.removeCookie('poseidon_inlineprofile_expanded', {path: '/'});
    },

    restoreMenuState: function() {
        var menucookie = $.cookie('poseidon_expandeditems');
        if (menucookie) {
            this.expandedMenuitems = menucookie.split(',');
            for (var i = 0; i < this.expandedMenuitems.length; i++) {
                var id = this.expandedMenuitems[i];
                if (id) {
                    var menuitem = $("#" + this.expandedMenuitems[i].replace(/:/g, "\\:"));
                    menuitem.addClass('active-menuitem');
                    
                    var submenu = menuitem.children('ul');
                    if(submenu.length) {
                        submenu.show();
                    }
                }
            }
        }
        
        var inlineProfileCookie = $.cookie('poseidon_inlineprofile_expanded');
        if (inlineProfileCookie) {
            this.profileMenu.show().prev('.profile').addClass('profile-expanded');
        }
    },

    enableModal: function() {
        this.modal = this.wrapper.append('<div class="layout-mask"></div>').children('.layout-mask');
    },

    disableModal: function() {
        this.modal.remove();
    },

    enableSwipe: function() {
        var $this = this;
        this.menuWrapper.swipe({
            swipeLeft: function() {
                $this.menuButton.click();
            }
        });
    },

    disableSwipe: function() {
        this.menuWrapper.swipe('destroy');
    },

    isHorizontal: function() {
        return this.wrapper.hasClass('layout-horizontal') && this.isDesktop();
    },
    
    isSlim: function() {
        return this.wrapper.hasClass('layout-slim') && this.isDesktop();
    },
    
    isOverlay: function() {
        return this.wrapper.hasClass('layout-overlay') && this.isDesktop();
    },
    
    isStatic: function() {
        return this.wrapper.hasClass('layout-static') && this.isDesktop();
    },

    isTablet: function() {
        var width = window.innerWidth;
        return width <= 1024 && width > 640;
    },

    isDesktop: function() {
        return window.innerWidth > 1024;
    },

    isMobile: function() {
        return window.innerWidth <= 640;
    },

    _initRightSidebar: function() {
        var $this = this;
        
        this.rightSidebar = $('#right-sidebar');
        this.rightSidebarBtnOpen = $('#right-sidebar-button-open');
        this.rightSidebarBtnClose = $('#right-sidebar-button-close');

        $this.rightSidebar.children('.nano').nanoScroller({flash:true});
        
        this.rightSidebarBtnOpen.on('click', function(e) {
            $this.rightSidebar.addClass('right-sidebar-active');
            e.preventDefault();
        });
        
        this.rightSidebarBtnClose.on('click', function(e) {
            $this.rightSidebar.removeClass('right-sidebar-active');
            e.preventDefault();
        });
        
        this.rightSidebar.on('click', function() {
            setTimeout(function() {
                $this.rightSidebar.children('.nano').nanoScroller();
            }, 500);
        });
    },

    closeRightSidebarMenu: function() {
        if(this.rightSidebar) {
            this.rightSidebar.removeClass('right-sidebar-active');
        }
    }

    
});

/*!
 * jQuery Cookie Plugin v1.4.1
 * https://github.com/carhartl/jquery-cookie
 *
 * Copyright 2006, 2014 Klaus Hartl
 * Released under the MIT license
 */
(function (factory) {
	if (typeof define === 'function' && define.amd) {
		// AMD (Register as an anonymous module)
		define(['jquery'], factory);
	} else if (typeof exports === 'object') {
		// Node/CommonJS
		module.exports = factory(require('jquery'));
	} else {
		// Browser globals
		factory(jQuery);
	}
}(function ($) {

	var pluses = /\+/g;

	function encode(s) {
		return config.raw ? s : encodeURIComponent(s);
	}

	function decode(s) {
		return config.raw ? s : decodeURIComponent(s);
	}

	function stringifyCookieValue(value) {
		return encode(config.json ? JSON.stringify(value) : String(value));
	}

	function parseCookieValue(s) {
		if (s.indexOf('"') === 0) {
			// This is a quoted cookie as according to RFC2068, unescape...
			s = s.slice(1, -1).replace(/\\"/g, '"').replace(/\\\\/g, '\\');
		}

		try {
			// Replace server-side written pluses with spaces.
			// If we can't decode the cookie, ignore it, it's unusable.
			// If we can't parse the cookie, ignore it, it's unusable.
			s = decodeURIComponent(s.replace(pluses, ' '));
			return config.json ? JSON.parse(s) : s;
		} catch(e) {}
	}

	function read(s, converter) {
		var value = config.raw ? s : parseCookieValue(s);
		return $.isFunction(converter) ? converter(value) : value;
	}

	var config = $.cookie = function (key, value, options) {

		// Write

		if (arguments.length > 1 && !$.isFunction(value)) {
			options = $.extend({}, config.defaults, options);

			if (typeof options.expires === 'number') {
				var days = options.expires, t = options.expires = new Date();
				t.setMilliseconds(t.getMilliseconds() + days * 864e+5);
			}

			return (document.cookie = [
				encode(key), '=', stringifyCookieValue(value),
				options.expires ? '; expires=' + options.expires.toUTCString() : '', // use expires attribute, max-age is not supported by IE
				options.path    ? '; path=' + options.path : '',
				options.domain  ? '; domain=' + options.domain : '',
				options.secure  ? '; secure' : ''
			].join(''));
		}

		// Read

		var result = key ? undefined : {},
			// To prevent the for loop in the first place assign an empty array
			// in case there are no cookies at all. Also prevents odd result when
			// calling $.cookie().
			cookies = document.cookie ? document.cookie.split('; ') : [],
			i = 0,
			l = cookies.length;

		for (; i < l; i++) {
			var parts = cookies[i].split('='),
				name = decode(parts.shift()),
				cookie = parts.join('=');

			if (key === name) {
				// If second argument (value) is a function it's a converter...
				result = read(cookie, value);
				break;
			}

			// Prevent storing a cookie that we couldn't decode.
			if (!key && (cookie = read(cookie)) !== undefined) {
				result[name] = cookie;
			}
		}

		return result;
	};

	config.defaults = {};

	$.removeCookie = function (key, options) {
		// Must not alter options, thus extending a fresh object...
		$.cookie(key, '', $.extend({}, options, { expires: -1 }));
		return !$.cookie(key);
	};

}));

/* JS extensions to support material animations */
if (PrimeFaces.widget.InputSwitch) {
    PrimeFaces.widget.InputSwitch = PrimeFaces.widget.InputSwitch.extend({

        init: function (cfg) {
            this._super(cfg);

            if (this.input.prop('checked')) {
                this.jq.addClass('ui-inputswitch-checked');
            }
        },

        toggle: function () {
            if (this.input.prop('checked')) {
                this.uncheck();
                this.jq.removeClass('ui-inputswitch-checked');
            } else {
                this.check();
                this.jq.addClass('ui-inputswitch-checked');
            }
        }
    });
}
    

