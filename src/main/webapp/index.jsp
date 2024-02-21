<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="parts/head.jsp" %>
    <%@include file="parts/meta.jsp" %>
    <%@include file="parts/navbar.jsp"%>
    <title>StayUp - Home</title>
</head>
<body>
<svg xmlns="http://www.w3.org/2000/svg" class="d-none">
    <symbol id="check2" viewBox="0 0 16 16">
        <path d="M13.854 3.646a.5.5 0 0 1 0 .708l-7 7a.5.5 0 0 1-.708 0l-3.5-3.5a.5.5 0 1 1 .708-.708L6.5 10.293l6.646-6.647a.5.5 0 0 1 .708 0z"></path>
    </symbol>
    <symbol id="circle-half" viewBox="0 0 16 16">
        <path d="M8 15A7 7 0 1 0 8 1v14zm0 1A8 8 0 1 1 8 0a8 8 0 0 1 0 16z"></path>
    </symbol>
    <symbol id="moon-stars-fill" viewBox="0 0 16 16">
        <path d="M6 .278a.768.768 0 0 1 .08.858 7.208 7.208 0 0 0-.878 3.46c0 4.021 3.278 7.277 7.318 7.277.527 0 1.04-.055 1.533-.16a.787.787 0 0 1 .81.316.733.733 0 0 1-.031.893A8.349 8.349 0 0 1 8.344 16C3.734 16 0 12.286 0 7.71 0 4.266 2.114 1.312 5.124.06A.752.752 0 0 1 6 .278z"></path>
        <path d="M10.794 3.148a.217.217 0 0 1 .412 0l.387 1.162c.173.518.579.924 1.097 1.097l1.162.387a.217.217 0 0 1 0 .412l-1.162.387a1.734 1.734 0 0 0-1.097 1.097l-.387 1.162a.217.217 0 0 1-.412 0l-.387-1.162A1.734 1.734 0 0 0 9.31 6.593l-1.162-.387a.217.217 0 0 1 0-.412l1.162-.387a1.734 1.734 0 0 0 1.097-1.097l.387-1.162zM13.863.099a.145.145 0 0 1 .274 0l.258.774c.115.346.386.617.732.732l.774.258a.145.145 0 0 1 0 .274l-.774.258a1.156 1.156 0 0 0-.732.732l-.258.774a.145.145 0 0 1-.274 0l-.258-.774a1.156 1.156 0 0 0-.732-.732l-.774-.258a.145.145 0 0 1 0-.274l.774-.258c.346-.115.617-.386.732-.732L13.863.1z"></path>
    </symbol>
    <symbol id="sun-fill" viewBox="0 0 16 16">
        <path d="M8 12a4 4 0 1 0 0-8 4 4 0 0 0 0 8zM8 0a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 0zm0 13a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 13zm8-5a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2a.5.5 0 0 1 .5.5zM3 8a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2A.5.5 0 0 1 3 8zm10.657-5.657a.5.5 0 0 1 0 .707l-1.414 1.415a.5.5 0 1 1-.707-.708l1.414-1.414a.5.5 0 0 1 .707 0zm-9.193 9.193a.5.5 0 0 1 0 .707L3.05 13.657a.5.5 0 0 1-.707-.707l1.414-1.414a.5.5 0 0 1 .707 0zm9.193 2.121a.5.5 0 0 1-.707 0l-1.414-1.414a.5.5 0 0 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .707zM4.464 4.465a.5.5 0 0 1-.707 0L2.343 3.05a.5.5 0 1 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .708z"></path>
    </symbol>
</svg>
<main>
    <div id="myCarousel" class="carousel slide mb-6" data-bs-ride="carousel" style="height: 800px;overflow: hidden;">
        <div class="carousel-indicators">
            <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="0" class="" aria-label="Slide 1"></button>
            <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="1" aria-label="Slide 2" class=""></button>
            <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="2" aria-label="Slide 3" class="active" aria-current="true"></button>
        </div>
        <div class="carousel-inner">
            <div class="carousel-item carousel-item-next carousel-item-start">
                <img src="images/carousel1.svg" class="d-block w-100">
            </div>
            <div class="carousel-item active carousel-item-next carousel-item-start">
                <img src="images/carousel2.svg" class="d-block w-100">
            </div>
            <div class="carousel-item carousel-item-next carousel-item-start">
                <img src="images/carousel3.svg" class="d-block w-100">
            </div>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#myCarousel" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#myCarousel" data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
        </button>
    </div>


    <!-- Marketing messaging and featurettes
    ================================================== -->
    <!-- Wrap the rest of the page in another container to center all the content. -->

    <div class="container marketing">
        <!-- START THE FEATURETTES -->

        <hr class="featurette-divider">

        <div class="row featurette">
            <div class="col-md-7">
                <h2 class="featurette-heading fw-normal lh-1 text-black">Add your parameters. <span class="text-primary">It’ll blow your mind.</span></h2>
                <p class="lead text-black" style="text-align: justify">Customize your fitness journey like never before with our latest feature! Tailor your experience by inputting your own personal parameters, allowing seamless communication between you and your personal trainer. Get started now and elevate your fitness experience with personalized precision.</p>
            </div>
            <div class="col-md-5">
                <img src="images/parameters.png" class="img-fluid mx-auto">
            </div>
        </div>

        <hr class="featurette-divider">

        <div class="row featurette">
            <div class="col-md-7 order-md-2">
                <h2 class="featurette-heading fw-normal lh-1 text-black">Request a subscription. <span class="text-primary">See for yourself.</span></h2>
                <p class="lead text-black" style="text-align: justify">Ready to take your fitness goals to the next level? Subscribe now to access personalized training sessions with a certified personal trainer! Tailor your workouts, set achievable targets, and receive expert guidance tailored to your needs. Elevate your fitness journey and achieve results faster. Subscribe today and embark on a transformative path to a healthier, stronger you!</p>
            </div>
            <div class="col-md-5 order-md-1">
                <img src="images/subscription.png" class="img-fluid mx-auto">
            </div>
        </div>

        <hr class="featurette-divider">

        <div class="row featurette">
            <div class="col-md-7">
                <h2 class="featurette-heading fw-normal lh-1 text-black">Check your training plans. <span class="text-primary">Checkmate.</span></h2>
                <p class="lead text-black" style="text-align: justify">Experience the power of tailored fitness with our latest feature! Easily view and access your personalized workout plans directly from your dashboard. Whether you're at the gym or working out from home, stay on track with customized routines designed specifically for you. From strength training to cardio, your workouts are just a click away. Start maximizing your fitness potential today with personalized workout plans that fit your lifestyle and goals.</p>
            </div>
            <div class="col-md-5">
                <img src="images/trainingplan.png" class="img-fluid mx-auto">
            </div>
        </div>

        <hr class="featurette-divider">

        <!-- /END THE FEATURETTES -->

    </div><!-- /.container -->
</main>
<script>
    // Funzione per centrare l'immagine nel carousel
    function centerImage() {
        var carouselHeight = $('#myCarousel').height();
        $('.carousel-item img').each(function() {
            var imageHeight = $(this).height();
            var topMargin = (carouselHeight - imageHeight) / 2;
            $(this).css('margin-top', topMargin + 'px');
        });
    }

    // Chiamata alla funzione per centrare l'immagine quando la finestra viene ridimensionata
    $(window).resize(centerImage);
    // Chiamata alla funzione per centrare l'immagine al caricamento della pagina
    $(document).ready(centerImage);
</script>
<%@include file="parts/footer.jsp"%>
</body>
</html>
