//animation effect(waypoint)
//paste this code under head tag or in a seperate js file.
 // Wait for window load
 $(window).load(function() {
  // Animate loader off screen
  $(".se-pre-con").fadeOut("slow");

  function onScrollInit( items, trigger ) {
    items.each( function() {
      var osElement = $(this),
      osAnimationClass = osElement.attr('data-os-animation'),
      osAnimationDelay = osElement.attr('data-os-animation-delay');

      osElement.css({
        '-webkit-animation-delay':  osAnimationDelay,
        '-moz-animation-delay':     osAnimationDelay,
        'animation-delay':          osAnimationDelay
      });

      var osTrigger = ( trigger ) ? trigger : osElement;

      osTrigger.waypoint(function() {
        osElement.addClass('animated').addClass(osAnimationClass);
      },{
        triggerOnce: true,
        offset: '90%'
      });
    });
  }

  onScrollInit( $('.os-animation') );
  onScrollInit( $('.staggered-animation'), $('.staggered-animation-container')  


    );
});


 $(window).scroll(function() {

  if ($(this).scrollTop() > 1){
    $('header').addClass("sticky");
  }
  else{
    $('header').removeClass("sticky");
  }
});

 //smoothscroll
 $('a[href^="#"]').on('click', function (e) {
  e.preventDefault();  
  
  $('a').each(function () {
    $(this).removeClass('active');
  })
  $(this).addClass('active');
  var target = this.hash,
  menu = target;
  $target = $(target);
  $('html, body').stop().animate({
    'scrollTop': $target.offset().top+0
  }, 500, 'swing', function () {
    window.location.hash = target;           
  });
});

$(document).ready(function(){
    $('.modal').on("hidden.bs.modal", function(){
        console.log("modal closed");
        $(this).find('form').trigger('reset');
    });
    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('#img-upload').attr('src', e.target.result);
            }
            reader.readAsDataURL(input.files[0]);
        }
    }
    $("#imgInp").change(function(){
        readURL(this);
    });
    // ('#submit-add-form').click(function() {
    //     var name = $('#product-name').val();
    //     var price = $('#product-price').val();
    //     var description = $('#product-description').val();
    //     var category = $('input[name=radio]:checked').val();
    //     var image = [];
    //     console.log(name,price, description, category);
    //     $.ajax({
    //         type: "POST",
    //         url: "http://188.166.91.64:8080/Nukupi_war/rest/products",
    //         headers: {
    //             "Content-Type": "application/json"
    //          },
    //        data: {
    //            "title": name,
    //            "description": description,
    //            "category": category,
    //            "authorID": "3",
    //            "price": price,
    //            "images": image
    //        });
    // });
    // });

});




