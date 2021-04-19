package com.mixno.web_debugger.app;

public class JSManager {

    private static String CONTENTEDITABLE_ON = "document.getElementsByTagName('body')[0].setAttribute('contenteditable', 'true'); contentEditableBooleanEI = true;";
    private static String CONTENTEDITABLE_OFF = "document.getElementsByTagName('body')[0].setAttribute('contenteditable', 'null'); contentEditableBooleanEI = false;";
    public static String CONTENTEDITABLE = "if (contentEditableBooleanEI == null) {var contentEditableBooleanEI = false;}" +
            "if (contentEditableBooleanEI == false) {" + CONTENTEDITABLE_ON + "} else {" + CONTENTEDITABLE_OFF + "}";








    private static String HIGHLIGHT_BORDER_ON = "function setSourceElementOnEI() {\n" +
            "\tvar bodyInnerElement = document.getElementsByTagName('body')[0].innerHTML;\n" +
            "\tdocument.getElementsByTagName('body')[0].innerHTML = bodyInnerElement +\n" +
            "\t'<style type=\"text/css\">.eiTagBorderDS {box-shadow: 0 1px 2px rgba(0,0,0,.2);}.eiTagBorderDS:active {cursor: pointer; box-shadow: 0 2px 7px rgba(0,0,0,.4);}</style>' +\n" +
            "\t'';\n" +
            "\n" +
            "\tvar elmEIDS = document.getElementsByTagName(\"*\");\n" +
            "\tfor(var i = 0, all = elmEIDS.length; i < all; i++){\n" +
            "\t    elmEIDS[i].classList.add('eiTagBorderDS');\n" +
            "\t    highlightBorderEnable = true;\n" +
            "\t}\n" +
            "}";
    private static String HIGHLIGHT_BORDER_OFF = "function setSourceElementOffEI() {\n" +
            "\tvar elmEIDS = document.getElementsByTagName(\"*\");\n" +
            "\tfor(var i = 0, all = elmEIDS.length; i < all; i++){\n" +
            "\t    elmEIDS[i].classList.remove('eiTagBorderDS');\n" +
            "\t    highlightBorderEnable = false;\n" +
            "\t}\n" +
            "}";
    public static String HIGHLIGHT_BORDER = HIGHLIGHT_BORDER_ON + "\n\n" + HIGHLIGHT_BORDER_OFF + "\n\n if (highlightBorderEnable == null) {var highlightBorderEnable = false;}" +
            "if (highlightBorderEnable == false) {setSourceElementOnEI();} else {setSourceElementOffEI();}";

    public static String HIGHLIGHT_BORDER_V2 = "(function(){\n" +
            "\tvar ELEMENTS_EI_BODY = document.body.getElementsByTagName(\"*\");\n" +
            "\n" +
            "\tif (document.querySelector('style#STYLE_EI') != null) {\n" +
            "\t\tdocument.getElementById('STYLE_EI').remove();\n" +
            "\t\tfor(var i = 0, all = ELEMENTS_EI_BODY.length; i < all; i++){\n" +
            "\t\t    ELEMENTS_EI_BODY[i].classList.remove('EI_TAG_BORDER');\n" +
            "\t\t}\n" +
            "\t} else {\n" +
            "\t\tvar SOURCE_CODE_STYLE = document.createElement('style');\n" +
            "\t\t\tSOURCE_CODE_STYLE.id = 'STYLE_EI';\n" +
            "\t\t\tSOURCE_CODE_STYLE.type = 'text/css';\n" +
            "\t\t\tSOURCE_CODE_STYLE.innerHTML = '.EI_TAG_BORDER {outline: 2px dashed #8AA9B8;outline-offset: -2px;}';\n" +
            "\n" +
            "\t\tdocument.body.appendChild(SOURCE_CODE_STYLE);\n" +
            "\n" +
            "\t\tfor(var i = 0, all = ELEMENTS_EI_BODY.length; i < all; i++){\n" +
            "\t\t    ELEMENTS_EI_BODY[i].classList.add('EI_TAG_BORDER');\n" +
            "\t\t}\n" +
            "\t}\n" +
            "})();";





    public static String ELEMENT_EDITOR = "(function(){\n" +
            "\tvar scriptElementEIIIII = document.createElement('script');\n" +
            "\tscriptElementEIIIII.type = 'text/javascript';\n" +
            "\tscriptElementEIIIII.src = 'https://doctorsteep.ru/ei/js/codeElement/eiCodeElement.js?v=1" + Data.getRandomNum(0, 999) + "';" +
            "\tdocument.head.appendChild(scriptElementEIIIII);\n" +
            "})();";





    public static String SOURCE = "(function(){\n" +
            "    var bodyOuter = document.body.outerHTML;\n" +
            "    var htmlOuter = document.documentElement.outerHTML;\n" +
            "    document.body.innerHTML +=\n" +
            "    '<ei_tag id=\"ei_tag\">' +\n" +
            "        '<style id=\"styleEIDS0\" type=\"text/css\"></style>' +\n" +
            "        '<style id=\"styleEIDS1\" type=\"text/css\">div.eiAlertDS {display: flex;position: fixed;top: 0;bottom: 0;left: 0;right: 0;background-color: rgba(0,0,0,.3); z-index: 999;align-items: center;justify-content: center;}</style>' +\n" +
            "        '<style id=\"styleEIDS2\" type=\"text/css\">div.eiAlertContent {display: block;background-color: rgba(255,255,255,1);width: 90%;height: auto;z-index: 1000;box-shadow: 0 2px 4px rgba(0,0,0,.6);}</style>' +\n" +
            "        '<style id=\"styleEIDS3\" type=\"text/css\">textarea.eiTextareaDS {display: block;width: 96%;height: 70vh;resize: none;color: rgba(0,0,0,.9);font-size: 13px;outline: none;border: none;margin: 0;margin-left: 10px;padding: 0;}</style>' +\n" +
            "        '<style id=\"styleEIDS4\" type=\"text/css\">h2.eiAlertTitle {font-family: sans-serif;font-weight: 600;font-size: 15px;color: rgba(0,0,0,1);outline: none;border: none;background-color: rgba(0,0,0,.1);margin: 0;padding: 10px;user-select: none;}</style>' +\n" +
            "        '<style id=\"styleEIDS5\" type=\"text/css\">div.eiBottomContent {width: 100%;padding: 0;margin: 0;display: flex;align-items: center;}</style>' +\n" +
            "        '<style id=\"styleEIDS6\" type=\"text/css\">button.buttonEIAlert {background-color: rgba(0,0,0,.1);color: rgba(0,0,0,.8);border-radius: 2px;padding: 7px;margin: 8px;user-select: none;outline: unset;border: 1px solid rgba(0,0,0,.15);}button.buttonEIAlert:active {box-shadow: 0 2px 3px rgba(0,0,0,.3);}</style>' +\n" +
            "        '<style id=\"styleEIDS7\" type=\"text/css\">input.seEIDS {width: 95%; margin: 10px; padding: 8px;}</style>' +\n" +
            "        '<div class=\"eiAlertDS\" id=\"eiAlertDS\">' +\n" +
            "            '<div class=\"eiAlertContent\">' +\n" +
            "                '<h2 class=\"eiAlertTitle\">Source code page</h2>' +\n" +
            "                '<!--<input type=\"search\" name=\"search\" id=\"seEIDS\" class=\"seEIDS\" placeholder=\"Search text\" autocomplete=\"off\">-->' +\n" +
            "                '<textarea class=\"eiTextareaDS\" id=\"eiTextareaDS\">' + htmlOuter + '</textarea>' +\n" +
            "                '<div class=\"eiBottomContent\">' +\n" +
            "                    '<button class=\"buttonEIAlert\" id=\"save\">Save</button>' +\n" +
            "                    '<button class=\"buttonEIAlert\" id=\"close\">Close</button>' +\n" +
            "                '</div>' +\n" +
            "            '</div>' +\n" +
            "        '</div>' +\n" +
            "    '</ei_tag>';\n" +
            "\n" +
            "    document.getElementById(\"close\").addEventListener(\"click\", function() {\n" +
            "       document.getElementById('ei_tag').remove();\n" +
            "    });\n" +
            "    document.getElementById(\"save\").addEventListener(\"click\", function() {\n" +
            "        document.documentElement.innerHTML = document.getElementById('eiTextareaDS').value;\n" +
            "    });\n" +
            "})();";
    public static String SOURCE_V2 = "(function () {\n"+
            "\tvar SOURCE_CODE = document.documentElement.outerHTML;\n" +
            "\n" +
            "\t// STEEP-1\n" +
            "\tvar SOURCE_CODE_DIV = document.createElement('div');\n" +
            "        SOURCE_CODE_DIV.id = 'SOURCE_CODE_EI_DIV';\n" +
            "        SOURCE_CODE_DIV.style.display = 'flex';\n" +
            "        SOURCE_CODE_DIV.style.position = 'fixed';\n" +
            "        SOURCE_CODE_DIV.style.alignItems = 'center';\n" +
            "        SOURCE_CODE_DIV.style.justifyContent = 'center';\n" +
            "        SOURCE_CODE_DIV.style.padding = '0';\n" +
            "        SOURCE_CODE_DIV.style.margin = '0';\n" +
            "        SOURCE_CODE_DIV.style.width = '-webkit-fill-available';\n" +
            "        SOURCE_CODE_DIV.style.height = '-webkit-fill-available';\n" +
            "        SOURCE_CODE_DIV.style.backgroundColor = 'rgba(0,0,0,.5)';\n" +
            "        SOURCE_CODE_DIV.style.zIndex = '999999';\n" +
            "        SOURCE_CODE_DIV.style.top = '0';\n" +
            "        // SOURCE_CODE_DIV.innerHTML = '';\n" +
            "\n" +
            "    document.body.appendChild(SOURCE_CODE_DIV);\n" +
            "\n" +
            "\n" +
            "    // STEEP-2\n" +
            "    var SOURCE_CODE_DIV_DIALOG = document.createElement('div');\n" +
            "        SOURCE_CODE_DIV_DIALOG.id = 'SOURCE_CODE_EI_DIV_DIALOG';\n" +
            "        SOURCE_CODE_DIV_DIALOG.style.width = '-webkit-fill-available';\n" +
            "        SOURCE_CODE_DIV_DIALOG.style.padding = '6px';\n" +
            "        SOURCE_CODE_DIV_DIALOG.style.margin = '10px';\n" +
            "        SOURCE_CODE_DIV_DIALOG.style.backgroundColor = '#FFFFFF';\n" +
            "        SOURCE_CODE_DIV_DIALOG.style.borderRadius = '6px';\n" +
            "\n" +
            "    document.getElementById('SOURCE_CODE_EI_DIV').appendChild(SOURCE_CODE_DIV_DIALOG);\n" +
            "\n" +
            "\n" +
            "    // STEEP-3\n" +
            "    var SOURCE_CODE_DIV_TITLE = document.createElement('h2');\n" +
            "        SOURCE_CODE_DIV_TITLE.id = 'SOURCE_CODE_EI_TITLE';\n" +
            "        SOURCE_CODE_DIV_TITLE.textContent = 'Source page - '+document.title+'';\n" +
            "        SOURCE_CODE_DIV_TITLE.style.padding = '4px';\n" +
            "        SOURCE_CODE_DIV_TITLE.style.margin = '0';\n" +
            "        SOURCE_CODE_DIV_TITLE.style.width = '-webkit-fill-available';\n" +
            "        SOURCE_CODE_DIV_TITLE.style.userSelect = 'none';\n" +
            "        SOURCE_CODE_DIV_TITLE.style.fontFamily = 'system-ui';\n" +
            "        SOURCE_CODE_DIV_TITLE.style.fontWeight = '500';\n" +
            "        SOURCE_CODE_DIV_TITLE.style.fontSize = '14px';\n" +
            "        SOURCE_CODE_DIV_TITLE.style.color = '#000000';\n" +
            "\n" +
            "    var SOURCE_CODE_DIV_TEXTAREA = document.createElement('textarea');\n" +
            "        SOURCE_CODE_DIV_TEXTAREA.id = 'SOURCE_CODE_EI_TEXTAREA';\n" +
            "        SOURCE_CODE_DIV_TEXTAREA.textContent = SOURCE_CODE;\n" +
            "        SOURCE_CODE_DIV_TEXTAREA.style.width = '-webkit-fill-available';\n" +
            "        SOURCE_CODE_DIV_TEXTAREA.style.maxWidth = '-webkit-fill-available';\n" +
            "        SOURCE_CODE_DIV_TEXTAREA.style.minWidth = '-webkit-fill-available';\n" +
            "        SOURCE_CODE_DIV_TEXTAREA.style.resize = 'vertical';\n" +
            "        SOURCE_CODE_DIV_TEXTAREA.style.minHeight = '82vh';\n" +
            "        SOURCE_CODE_DIV_TEXTAREA.style.maxHeight = '82vh';\n" +
            "        SOURCE_CODE_DIV_TEXTAREA.style.border = '1px solid #000000';\n" +
            "        SOURCE_CODE_DIV_TEXTAREA.style.outline = 'none';\n" +
            "        SOURCE_CODE_DIV_TEXTAREA.style.backgroundColor = '#F1F1F1';\n" +
            "        SOURCE_CODE_DIV_TEXTAREA.style.margin = '4px';\n" +
            "        SOURCE_CODE_DIV_TEXTAREA.style.fontFamily = 'monospace';\n" +
            "        SOURCE_CODE_DIV_TEXTAREA.style.fontWeight = '400';\n" +
            "        SOURCE_CODE_DIV_TEXTAREA.style.fontSize = '12px';\n" +
            "        SOURCE_CODE_DIV_TEXTAREA.style.padding = '5px';\n" +
            "        SOURCE_CODE_DIV_TEXTAREA.style.color = '#000000';\n" +
            "        SOURCE_CODE_DIV_TEXTAREA.style.display = 'flex';\n" +
            "\n" +
            "    var SOURCE_CODE_DIV_BUTTONS = document.createElement('div');\n" +
            "        SOURCE_CODE_DIV_BUTTONS.id = 'SOURCE_CODE_EI_DIV_BUTTONS';\n" +
            "        SOURCE_CODE_DIV_BUTTONS.style.display = 'flex';\n" +
            "        SOURCE_CODE_DIV_BUTTONS.style.alignItems = 'center';\n" +
            "        SOURCE_CODE_DIV_BUTTONS.style.padding = '0';\n" +
            "        SOURCE_CODE_DIV_BUTTONS.style.margin = '4px';\n" +
            "        SOURCE_CODE_DIV_BUTTONS.style.width = '-webkit-fill-available';\n" +
            "\n" +
            "    document.getElementById('SOURCE_CODE_EI_DIV_DIALOG').appendChild(SOURCE_CODE_DIV_TITLE);\n" +
            "    document.getElementById('SOURCE_CODE_EI_DIV_DIALOG').appendChild(SOURCE_CODE_DIV_TEXTAREA);\n" +
            "    document.getElementById('SOURCE_CODE_EI_DIV_DIALOG').appendChild(SOURCE_CODE_DIV_BUTTONS);\n" +
            "\n" +
            "\n" +
            "    // STEEP-4\n" +
            "    var SOURCE_CODE_DIV_BUTTONS_BTN1 = document.createElement('button');\n" +
            "        SOURCE_CODE_DIV_BUTTONS_BTN1.id = 'SOURCE_CODE_EI_DIV_BUTTON1';\n" +
            "        SOURCE_CODE_DIV_BUTTONS_BTN1.textContent = 'Save';\n" +
            "        SOURCE_CODE_DIV_BUTTONS_BTN1.title = 'Save changes';\n" +
            "        SOURCE_CODE_DIV_BUTTONS_BTN1.style.border = '1px solid #000000';\n" +
            "        SOURCE_CODE_DIV_BUTTONS_BTN1.style.padding = '5px 8px';\n" +
            "        SOURCE_CODE_DIV_BUTTONS_BTN1.style.margin = '0';\n" +
            "        SOURCE_CODE_DIV_BUTTONS_BTN1.style.color = '#000000';\n" +
            "        SOURCE_CODE_DIV_BUTTONS_BTN1.style.fontSize = '12px';\n" +
            "        SOURCE_CODE_DIV_BUTTONS_BTN1.style.fontWeight = '400';\n" +
            "        SOURCE_CODE_DIV_BUTTONS_BTN1.style.fontFamily = 'system-ui';\n" +
            "        SOURCE_CODE_DIV_BUTTONS_BTN1.style.userSelect = 'none';\n" +
            "        SOURCE_CODE_DIV_BUTTONS_BTN1.style.cursor = 'pointer';\n" +
            "        SOURCE_CODE_DIV_BUTTONS_BTN1.style.outline = 'none';\n" +
            "\n" +
            "    var SOURCE_CODE_DIV_BUTTONS_BTN2 = document.createElement('button');\n" +
            "        SOURCE_CODE_DIV_BUTTONS_BTN2.id = 'SOURCE_CODE_EI_DIV_BUTTON2';\n" +
            "        SOURCE_CODE_DIV_BUTTONS_BTN2.textContent = 'Cancel';\n" +
            "        SOURCE_CODE_DIV_BUTTONS_BTN2.title = 'Cancel changes';\n" +
            "        SOURCE_CODE_DIV_BUTTONS_BTN2.style.border = '1px solid #000000';\n" +
            "        SOURCE_CODE_DIV_BUTTONS_BTN2.style.padding = '5px 8px';\n" +
            "        SOURCE_CODE_DIV_BUTTONS_BTN2.style.margin = '0';\n" +
            "        SOURCE_CODE_DIV_BUTTONS_BTN2.style.color = '#000000';\n" +
            "        SOURCE_CODE_DIV_BUTTONS_BTN2.style.fontSize = '12px';\n" +
            "        SOURCE_CODE_DIV_BUTTONS_BTN2.style.fontWeight = '400';\n" +
            "        SOURCE_CODE_DIV_BUTTONS_BTN2.style.fontFamily = 'system-ui';\n" +
            "        SOURCE_CODE_DIV_BUTTONS_BTN2.style.userSelect = 'none';\n" +
            "        SOURCE_CODE_DIV_BUTTONS_BTN2.style.cursor = 'pointer';\n" +
            "        SOURCE_CODE_DIV_BUTTONS_BTN2.style.outline = 'none';\n" +
            "        SOURCE_CODE_DIV_BUTTONS_BTN2.style.marginLeft = '6px';\n" +
            "\n" +
            "    document.getElementById('SOURCE_CODE_EI_DIV_BUTTONS').appendChild(SOURCE_CODE_DIV_BUTTONS_BTN1);\n" +
            "    document.getElementById('SOURCE_CODE_EI_DIV_BUTTONS').appendChild(SOURCE_CODE_DIV_BUTTONS_BTN2);\n" +
            "\n" +
            "\n" +
            "    document.getElementById(\"SOURCE_CODE_EI_DIV_BUTTON1\").addEventListener(\"click\", function() {\n" +
            "        document.documentElement.innerHTML = document.getElementById('SOURCE_CODE_EI_TEXTAREA').value;\n" +
            "        try {\n" +
            "            window.$$.showAdsWeb();\n" +
            "        } catch (exx) {}\n" +
            "    });\n" +
            "    document.getElementById(\"SOURCE_CODE_EI_DIV_BUTTON2\").addEventListener(\"click\", function() {\n" +
            "        try {\n" +
            "            document.getElementById('SOURCE_CODE_EI_DIV').remove();\n" +
            "        } catch (exx) {}\n" +
            "    });\n"+
            "})();";


    public static String SYSTEM = "(function () {\n" +
            "\tvar parentEI = document.getElementsByTagName('head').item(0);\n" +
            "\tvar styleEI = document.createElement('script');\n" +
            "\tstyleEI.type = 'text/javascript';\n" +
            "\tstyleEI.src = 'https://doctorsteep.ru/ei/js/system.js?v=5';\n" +
            "\tparentEI.appendChild(styleEI);\n" +
            "})();";

    public static String DEVTOOLS = "(function () {\n" +
            "    var SOURCE_CODE_SCRIPT_DEVTOOLS = document.createElement('script');\n" +
            "        SOURCE_CODE_SCRIPT_DEVTOOLS.src = 'https://mixno.ru/element-inspector/js/devtools.js';\n" +
            "\n" +
            "    document.head.appendChild(SOURCE_CODE_SCRIPT_DEVTOOLS);\n" +
            "})();";



    public static String SOURCE_CODE_STYLE_EI = "(function () {\n" +
            "\tif (document.querySelector('script#SOURCE_SCRIPT_CLICK') != null) {\n" +
            "\t\tvar ELEMENTS_BODY = document.body.getElementsByTagName('*');\n" +
            "\t\tfor(var i = 0, all = ELEMENTS_BODY.length; i < all; i++) {\n" +
            "\t\t\tELEMENTS_BODY[i].setAttribute('onclick', null);\n" +
            "\t\t}\n" +
            "\t\tdocument.getElementById('SOURCE_SCRIPT_CLICK').remove();\n" +
            "\t} else {\n" +
            "\t\tvar SOURCE_SCRIPT_CLICK = document.createElement('script');\n" +
            "\t\t\tSOURCE_SCRIPT_CLICK.id = 'SOURCE_SCRIPT_CLICK';\n" +
            "\t\t\tSOURCE_SCRIPT_CLICK.type = 'text/javascript';\n" +
            "\t\tdocument.body.appendChild(SOURCE_SCRIPT_CLICK);\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\t\tvar ELEMENTS_BODY = document.body.getElementsByTagName('*');\n" +
            "\t\tfor(var i = 0, all = ELEMENTS_BODY.length; i < all; i++) {\n" +
            "\t\t\tvar NM = Math.random()*99999999999999999999;\n" +
            "\t\t\tELEMENTS_BODY[i].classList.add('EI_TAG_'+NM);\n" +
            "\t\t\tdocument.getElementById('SOURCE_SCRIPT_CLICK').innerHTML += 'var EI_TAG_'+NM+' = \"'+ELEMENTS_BODY[i].getAttribute('onclick')+'\";';\n" +
            "\t\t}\n" +
            "\t\tfor(var i = 0, all = ELEMENTS_BODY.length; i < all; i++) {\n" +
            "\t\t\tvar VAL = 'event.stopPropagation();event.preventDefault();alert(getComputedStyle(this, null))';\n" +
            "\t\t\tELEMENTS_BODY[i].setAttribute('onclick', VAL);\n" +
            "\t\t}\n" +
            "\t}\n" +
            "})();";
    public static String SOURCE_CODE_ELEMENT_EI = "window.touchblock=!window.touchblock;setTimeout(function(){$$.blocktoggle(window.touchblock)}, 100);";
    public static String SOURCE_PAGE_EI = "window.$$.processHTML(document.documentElement.outerHTML);";
    public static String LOG1_EI = "function injek(){window.hasovrde=1;var e=XMLHttpRequest.prototype.open;XMLHttpRequest.prototype.open=function(ee,nn,aa){this.addEventListener('load',function(){$$.log(this.responseText, nn, JSON.stringify(arguments))}),e.apply(this,arguments)}};if(window.hasovrde!=1){injek();}";
    public static String LOG2_EI = "function injek2(){window.touchblock=0,window.dummy1=1,document.addEventListener('click',function(n){if(1==window.touchblock){n.preventDefault();n.stopPropagation();var t=document.elementFromPoint(n.clientX,n.clientY);window.ganti=function(n){t.outerHTML=n},window.gantiparent=function(n){t.parentElement.outerHTML=n},$$.print(t.parentElement.outerHTML, t.outerHTML)}},!0)}1!=window.dummy1&&injek2();";
    public static String LOG3_EI = "function injek3(){window.hasdir=1;window.dir=function(n){var r=[];for(var t in n)'function'==typeof n[t]&&r.push(t);return r}};if(window.hasdir!=1){injek3();}";
    public static String FIREBUG_EI = "(function(F,i,r,e,b,u,g,L,I,T,E){if(F.getElementById(b))return;E=F[i+'NS']&&F.documentElement.namespaceURI;E=E?F[i+'NS'](E,'script'):F[i]('script');E[r]('id',b);E[r]('src',I+g+T);E[r](b,u);(F[e]('head')[0]||F[e]('body')[0]).appendChild(E);E=new Image;E[r]('src',I+L);})(document,'createElement','setAttribute','getElementsByTagName','FirebugLite','4','firebug-lite.js','releases/lite/latest/skin/xp/sprite.png','http://firebug-lite.s3.us-east-2.amazonaws.com/','#startOpened');";
    public static String ERUDA_EI = "(function () { var script =  document.createElement('script');script.src=\"//cdn.jsdelivr.net/npm/eruda\"; document.body.appendChild(script);script.onload = function () { eruda.init() } })();";
    public static String META_THEME_COLOR = "(function () { const metas = document.getElementsByTagName('meta'); for (let i = 0; i < metas.length; i++) { if (metas[i].getAttribute('name') === 'theme-color') { return window.$$.siteTheme(metas[i].getAttribute('content')); } } return ''; })();";
    public static String PC_MODE = "document.querySelector('meta[name=\"viewport\"').setAttribute('content', 'width=1024px, initial-scale=' + (document.documentElement.clientWidth/1024));";

}
