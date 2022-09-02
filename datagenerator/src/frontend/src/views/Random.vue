<template>
  <div class="randompage">
    <LoadingBar v-if="loading"/>
    <div class="randomcolumn1">
      <CourseCreator class="compcontainer"/>
      <GroupCreator class="compcontainer"/>
    </div>
    <div class="randomcolumn2">
      <CurriculumCreator class="compcontainer"/>
      <TaxonomyComponent class="compcontainer"/>
    </div>
     <div class="randomcolumn2">
      <userCreator class="compcontainer"/>
      <RoleDistribution class="compcontainer"/>
    </div>
    <div style="width:40%; margin-left: auto; margin-right: auto; margin-bottom: 10vh;">
      <div class="titlewrapper">
        <h3 class="sendText">Generate All</h3>
        <div class="titleborder"></div>
      </div>
      <div class="classicbuttonform">
        <button class="uploadbutton" style="font-size: 2vh; padding: 5px;" @click="generateAll()">Generate</button>
      </div>
    </div>
  </div>
</template>

<script>
import userCreator from '../components/UserCreator'
import CourseCreator from '../components/CourseCreator'
import GroupCreator from '../components/GroupCreator'
import RoleDistribution from '../components/RoleDistribution'
import TaxonomyComponent from '../components/Taxonomy.vue'
import CurriculumCreator from '../components/CurriculumCreator.vue'
import LoadingBar from "../components/LoadingBar";


export default {
    name: 'RandomComponent',
    
    components: {
    userCreator,
    CourseCreator,
    GroupCreator,
    RoleDistribution,
    TaxonomyComponent,
    CurriculumCreator,
    LoadingBar
  },

  data(){
    return{
      loading: false,
    }
  },

  methods: {

    generateAll: async function(){
      this.loading = true;
      await CourseCreator.methods.create()
      await GroupCreator.methods.create()
      await CurriculumCreator.methods.create()
      await TaxonomyComponent.methods.fill()
      await userCreator.methods.create()
      location.reload();
    }
  }
}

</script>

<style>

</style>