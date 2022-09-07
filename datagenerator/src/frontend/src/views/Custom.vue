<template>
  <div class="custompage">
    <LoadingBar v-if="loading"/>
    <div class="leftdiv">
      <div class="sizelimiter">
        <div class="randomcolumn1">
          <div class="titlewrapper">
            <h3 class="sendText">Templates</h3>
            <div class="titleborder"></div>
          </div>
        </div>
      </div>
      <div class="customcontainer">
        <div class="templatecontainer" id="templatecontainer"> 
          <ol>
            <li v-for="template in templates" :key="template.id">
              <div class="templatediv" @click.prevent="setChosen(template)" v-bind:id="template">{{ template }}</div>
              <div v-if="!(template=='KMU Template')&&!(template=='Hochschule Template')&&!(template=='Erwachsenenbildung Template')" class="deletediv">
                <img class="deleteimg" @click="deleteTemplate(template)" alt = "uploadimage" src = "../assets/delete-icon.svg"/>
              </div>
              <div class="downloaddiv">
                <img class="downloadimg" @click="downloadTemplate(template)" src = "../assets/downloadicon.svg"/>
              </div>
            </li>
          </ol>
        </div>
        <div class="classicbuttonform"> 
          <input class="universalbutton" type="button" value="Use Chosen Template" @click="useTemplate()"/>
        </div>
        <div class="uploadcontainer"> 
          <label class="upload">
            <input type="file" id="image-file" @change="changeUploadable()"/>
            <img class="uploadimg" alt = "uploadimage" src = "../assets/uploadicon.svg"/>
          </label>
        </div>
        <div class="classicbuttonform"> 
          <button class="uploadbutton" @click="uploadTemplate()">Upload {{toBeUploaded}}</button>
        </div>
      </div>
    </div>
    <div class="rightdiv">
      <div class="sizelimiter">
        <div class="titlewrapper">
          <h3 class="sendText">Add Random Users</h3>
          <div class="titleborder"></div>
        </div>
      </div>
      <form class="classicbuttonform">
        <input class="sendBar" type="number" id="usernmbr" placeholder="AMOUNT"/><br>
        <input class="sendButton" type="button" value="add users" @click="create()"/>
      </form>
    </div>
  </div>
</template>

<script>
import api from "../api/backend-api"
import LoadingBar from "../components/LoadingBar";


export default {

  name: 'CustomComponent',

  components: {
        LoadingBar
    },

  data(){
    return{
      reponse: [],
      templates: [{}],
      temp: "",
      toBeUploaded: "{no file selected}",
      loading: false,
    }
  },

  mounted(){
    window.addEventListener("keyup", this.checkKey);
  },

  created(){
    this.getTemplates();
  },

  unmounted(){
    window.removeEventListener("keyup", this.checkKey);
  },

  methods: {

    useTemplate: async function(){
      if (document.getElementsByClassName("chosen").length != 0){
        this.temp = document.getElementsByClassName("chosen")[0].innerHTML;
        this.loading = true;
        await api.runTemplate(this.temp);
        this.loading = false;
      }
        
    },

    deleteTemplate: async function(input){
      await api.deleteTemplate(input);
      this.getTemplates(); 
    },

    downloadTemplate: function(input){
      console.log('download started')
      api.downloadTemplate(input).then((response) => {
        this.downloadFile(response, input)
      })
      .catch(() => console.log('error occured'))
    },

    downloadFile: async function (resultByte, input) {
      let blob = new Blob([resultByte], {type: "application/zip"});
      let objectUrl = URL.createObjectURL(blob);
      let link = document.createElement('a');
      link.href = objectUrl;
      link.download = input;
      link.click();
      window.URL.revokeObjectURL(link.href);
    },

    getTemplates: function(){
      api.getFileStructure().then(response =>{
            this.templates = response.data;
        })
    },

    uploadTemplate: async function(){
      var formData = new FormData();
      var file = document.getElementById("image-file").files[0];
      formData.append('file',file)
      this.loading = true;
      await api.uploadTemplate(formData);
      this.getTemplates(); 
      this.loading = false;
    },

    setChosen: async function(input){
      if (document.getElementsByClassName("chosen").length != 0){
        document.getElementsByClassName("chosen")[0].setAttribute("class", "templatediv");
      }
      document.getElementById(input).setAttribute("class", "chosen");
    },

    changeUploadable: function(){
      this.toBeUploaded = document.getElementById("image-file").value;
    },
    
    create: async function(){
      if(document.getElementById("usernmbr").value < 1){
        alert("Can't create less than 1 user");
      } else {
        this.loading = true;
        await api.createUsers(document.getElementById("usernmbr").value)  
        this.loading = false;
      }
    },

    openUploadWindow: function(){
      document.getElementById('image-file').click();
    },

    checkKey(e) {
      if (e.keyCode === 13){
        this.useTemplate();
      }
      else if (e.keyCode === 32){
        this.openUploadWindow();
      } 
    }
  }
}
</script>

<style>

</style>