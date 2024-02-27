<form action="" method="GET" class="header__center">
     <input id="search-input" type="text" placeholder="Search for available houses" name="title">

<!-- search icon -->
      <button type="submit" style="border: none; background-color:white">
      <img class="searchIcon" src="../images/searchIcon.png">
      </button>

</form>

<?php

if(isset($_GET["title"])){
      $searchVal = $_GET["title"];

      $controller = new Controller();
      $filteredHomes = $controller->retrieveHomes($searchVal);

    // $view = new View();
    // $filteredHomes = $view->indexView();
      
      while($row = $filteredHomes->fetch_assoc()){
            if($searchVal === $row["title"]){
                  echo '<h4>Search Results</h4>
                  <div class="gallery">
                  <div class="item">
                      <img class="card-img-top" src="',$row["image"],'" alt="',$row["title"],'">
                      <h2>',$row["title"],'</h2>
                      
                      <p>Located at ',$row["location"],'</p>
                      <p>',$row["price"],' ETB</p>
                     

                  </div>
              </div>
              <hr>';
            }
           
      }
}

?>   
