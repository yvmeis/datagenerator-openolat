<template>
  <div>
    <LoadingBar v-if="loading"/>
    <div class="cleaningcontainer">
        <div class="titlewrapper">
            <h3 class="sendText">Cleaning OpenOLAT</h3>
            <div class="titleborder"></div>
        </div>
        <form class="classicbuttonform">
            <button @click.prevent="delUsers()" class="sendButton" style="margin-left: 3vw">Delete All Users</button>
            <button @click.prevent="delCourses()" class="sendButton">Delete All Courses</button>
            <button @click.prevent="delGroups()" class="sendButton">Delete All Groups</button>
            <button @click.prevent="delTax()" class="sendButton" style="margin-right: 3vw">Empty All Taxonomies</button>
        </form>
        <form class="classicbuttonform">
            <button @click.prevent="delAll()" class="sendButton">Delete All</button>
        </form>
    </div>
  </div>
</template>

<script>
import LoadingBar from "../components/LoadingBar";
import api from "../api/backend-api"

export default {

    name: 'CleaningComponent',

    components: {
        LoadingBar
    },

    data(){
        return{
            loading: false
        }
    },
    

    methods: {
        
        delUsers: async function(){
            this.loading = true;
            await api.deleteUsers()
            this.loading = false;
        },

        delCourses: async function(){
            this.loading = true;
            await api.deleteCourses()
            this.loading = false;
        },

        delGroups: async function(){
            this.loading = true;
            await api.deleteGroups()
            this.loading = false;
        },

        delTax: async function(){
            this.loading = true;
            await api.cleanTaxonomies()
            this.loading = false;
        },

        delAll: async function(){
            await this.delCourses()
            await this.delGroups()
            await this.delUsers()
            await this.delTax()
        },
    },
}
</script>

<style scoped src="../assets/inputPages.css">

</style>