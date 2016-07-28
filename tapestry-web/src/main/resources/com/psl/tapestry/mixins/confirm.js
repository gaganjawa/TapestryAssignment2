/**
 * 
 */

(function ($){
    T5.extendInitializers(function(){
          function confirmation(spec){
                $("#"+spec.id).bind("click", function(e){                      
                      if(!confirm(spec.message))
                           e.preventDefault();
                });
          }
          return { confirmation : confirmation}
    });
}) (jQuery);

/*alert("Are you Sure ?");
var Confirm = Class.create();



Confirm.prototype = {
        initialize: function(element, message) {
                this.message = message;
                Event.observe($(element), 'click', this.doConfirm.bindAsEventListener(this));
        },
        
        doConfirm: function(e) {
                if(! confirm(this.message))
                        e.stop();
        }
}*/