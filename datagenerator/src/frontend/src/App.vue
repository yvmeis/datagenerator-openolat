<template>
  <div class = "application">
    <div class = "navbar">
      <div class = navmenu1>
        <div>
          <router-link class="link" to = "/login" >Login</router-link>
          <router-link class="link" to = "/random" >Random</router-link>
          <router-link class="link" to = "/custom" >Custom</router-link>
          <router-link class="link" to = "/clean" >Cleaning</router-link>
          <router-link style="visibility: hidden" class="link" to = "/templatecreation" >Creator</router-link>
        </div>
        <button class="logout" @click.prevent="logout">
          <div>LOGOUT</div>
          <img class="logoutimg" alt = "logoutimage" src = "./assets/logout-icon.png"/>
        </button>
      </div>
    </div>
    <router-view class = "pages"></router-view>
    <div class="bottomnav">
      <router-link active-class="bottomlink" class="bottomlink" to = "/help">Help</router-link>
    </div>
  </div>
</template>

<style src = "@/assets/inputPages.css">

</style>

<script>
import router from "./router/index.js"

export default {

  name: 'App',

  data(){
    return{
    }
  },
    
  methods: {  

    logout: function(){
      localStorage.removeItem('password')
      router.push("/login")
    }
  },

  
}

</script>

