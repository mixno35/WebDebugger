function setSourcePageEI() { // Page source
    var bodyOuter = document.body.outerHTML;
    var htmlOuter = document.documentElement.outerHTML;
    document.body.innerHTML +=
    '<ei_tag id="ei_tag">' +
        '<style id="styleEIDS0" type="text/css"></style>' +
        '<style id="styleEIDS1" type="text/css">div.eiAlertDS {display: flex;position: fixed;top: 0;bottom: 0;left: 0;right: 0;background-color: rgba(0,0,0,.3); z-index: 999;align-items: center;justify-content: center;}</style>' +
        '<style id="styleEIDS2" type="text/css">div.eiAlertContent {display: block;background-color: rgba(255,255,255,1);width: 600px;height: auto;z-index: 1000;box-shadow: 0 2px 4px rgba(0,0,0,.6);}</style>' +
        '<style id="styleEIDS3" type="text/css">textarea.eiTextareaDS {display: block;width: 590px;height: 70vh;resize: none;color: rgba(0,0,0,.9);font-size: 13px;outline: none;border: none;margin: 0;margin-left: 10px;padding: 0;}</style>' +
        '<style id="styleEIDS4" type="text/css">h2.eiAlertTitle {font-family: sans-serif;font-weight: 600;font-size: 15px;color: rgba(0,0,0,1);outline: none;border: none;background-color: rgba(0,0,0,.1);margin: 0;padding: 10px;user-select: none;}</style>' +
        '<style id="styleEIDS5" type="text/css">div.eiBottomContent {width: 100%;padding: 0;margin: 0;display: flex;align-items: center;}</style>' +
        '<style id="styleEIDS6" type="text/css">button.buttonEIAlert {background-color: rgba(0,0,0,.1);color: rgba(0,0,0,.8);border-radius: 2px;padding: 7px;margin: 8px;user-select: none;outline: unset;border: 1px solid rgba(0,0,0,.15);}button.buttonEIAlert:active {box-shadow: 0 2px 3px rgba(0,0,0,.3);}</style>' +
        '<style id="styleEIDS7" type="text/css">input.seEIDS {width: 95%; margin: 10px; padding: 8px;}</style>' +
        '<div class="eiAlertDS" id="eiAlertDS">' +
            '<div class="eiAlertContent">' +
                '<h2 class="eiAlertTitle">Source code page</h2>' +
                '<input type="search" name="search" id="seEIDS" class="seEIDS" placeholder="Search text" autocomplete="off">' +
                '<textarea class="eiTextareaDS" id="eiTextareaDS">' + htmlOuter + '</textarea>' +
                '<div class="eiBottomContent">' +
                    '<button class="buttonEIAlert" id="save" onclick="document.documentElement.innerHTML = document.getElementById(\'eiTextareaDS\').value">Save</button>' +
                    '<button class="buttonEIAlert" id="close" onclick="document.getElementById(\'ei_tag\').remove()">Close</button>' +
                '</div>' +
            '</div>' +
        '</div>' +
    '</ei_tag>';
   }
// Powered by DoctorSteep - from project ElementInspector