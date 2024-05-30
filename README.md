Room Database:<br/>
	a. Entity DAO and Database<br/>
	b. app gradle: <br/>
	
    Plugin id("kotlin-kapt")
		
		Dependancies:
		//room and lifecycle dependencies
    		implementation("androidx.room:room-runtime:2.6.1")
   		 kapt("androidx.room:room-compiler:2.6.1")
   		 //coroutine support
   		 implementation("androidx.room:room-ktx: 2.3.0")
   		 implementation("androidx.activity:activity-ktx:1.9.0")

	
   c. Entity: Create a data class<br/>
	d. Create a DAO (interface)<br/>
	e. Create a database<br/>
	f. Create application to initialise database<br/>
	g. Update manifest<br/>
	h. Create function to add record<br/>
	i. Pass DAO into record adding function<br/>
	j. Create recycler view adapter (special care for update and delete buttons)<br/>
	k. Populate the recycler view from database<br/>
	l. update and delete buttons are functional<br/>

