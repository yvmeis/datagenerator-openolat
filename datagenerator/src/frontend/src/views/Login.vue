<template>
  <div class = "loginpage">
    <div class = "logincontainer">
      <div class="titlewrapper">
        <h3 class="sendText">Login</h3>
        <div class="titleborder"></div>
      </div>
      <div class = "title">Data Generator</div>
      <div class="logoform">
        <img class = "logo" alt = "OpenOlatLogo" src = "../assets/openolat.svg"/>
        <div class="logotext">OPENOLAT</div>
      </div>
      <form class="settingsform">
        <label class="settingslabel">URL of REST Endpoints:</label><br>
        <input @keyup.enter="submit" class="settingsInput" type="text" id="url"><br>
        <label class="settingslabel">Administrator Username:</label><br>
        <input @keyup.enter="submit" class="settingsInput" type="text" id="username"><br>
        <label class="settingslabel" >Administrator Password:</label><br>
        <input @keyup.enter="submit" class="settingsInput" type="password" id="password"><br>
      </form>
      <form class="settingsform">
        <button @click.prevent="submit" class="loginbutton">
          <div>LOGIN</div>
          <img class="loginimg" alt = "loginimage" src = "../assets/login-icon.svg"/>
        </button>
      </form>
    </div>
  </div>
</template>

<script>
import router from "../router/index.js"
import api from "../api/backend-api"

export default {

  name: 'LoginComponent',

  data(){
    return{
      encryptedPassword: ""
    }
  },

  methods: {  
      submit: async function(){
        await api.login(document.getElementById("password").value).then(response =>{
          this.encryptedPassword = response.data;
        })
        localStorage.setItem('url', document.getElementById("url").value)
        localStorage.setItem('username', document.getElementById("username").value)
        localStorage.setItem('password', this.encryptedPassword)
        router.push('/random')      
      },
  },

  async mounted() {
    document.getElementById("url").value = localStorage.getItem('url');
    document.getElementById("username").value = localStorage.getItem('username');
    document.getElementById("password").value = "";
  }
}
</script>

<style scoped src="../assets/login.css">

</style>