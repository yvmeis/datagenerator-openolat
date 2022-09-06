<template>
  <div class="helppage">
    <div v-if="onStartPage" class="helppagebarstart">
      <div class="helppagetitle" @click="set1">Navigating the Generator</div>
      <div class="helppagetitle" @click="set2">Generating Random Data</div>
      <div class="helppagetitle" @click="set3">Using Custom Templates</div>
      <div class="helppagetitle" @click="set4">Setting up Custom Templates</div>
    </div>
    <div v-if="!onStartPage" class="helppagebaronpage">
      <div class="dummyhelppagetitle" @click="setStart">Navigating the Generator</div>
    </div>
    <div class="helppagecomponentcontainer">
      
      <NavigationHelper v-if="onPage1" class="helppagecomponent"/>
      
      <RandomHelper v-if="onPage2" class="helppagecomponent"/>

      <CustomHelper v-if="onPage3" class="helppagecomponent"/>

      <FileHelper v-if="onPage4" class="helppagecomponent"/>
      
    </div>
  </div>
</template>

<script>
import NavigationHelper from '@/components/NavigationHelper.vue';
import RandomHelper from '@/components/RandomHelper.vue';
import CustomHelper from '@/components/CustomHelper.vue';
import FileHelper from '@/components/FileHelper.vue';

export default {
    name: "HelpComponent",
    data() {
        return {
            onStartPage: true,
            onPage1: false,
            onPage2: false,
            onPage3: false,
            onPage4: false,
        };
    },
    mounted() {
        this.onStartPage = true;
        this.onPage1 = false;
        this.onPage2 = false;
        this.onPage3 = false;
        this.onPage4 = false;
    },
    methods: {
        setStart: function () {
            this.onStartPage = true;
            this.onPage1 = false;
            this.onPage2 = false;
            this.onPage3 = false;
            this.onPage4 = false;
        },
        set1: function () {
            this.onStartPage = false;
            this.onPage1 = true;
            this.onPage2 = false;
            this.onPage3 = false;
            this.onPage4 = false;
        },
        set2: function () {
            this.onStartPage = false;
            this.onPage1 = false;
            this.onPage2 = true;
            this.onPage3 = false;
            this.onPage4 = false;
        },
        set3: function () {
            this.onStartPage = false;
            this.onPage1 = false;
            this.onPage2 = false;
            this.onPage3 = true;
            this.onPage4 = false;
        },
        set4: function () {
            this.onStartPage = false;
            this.onPage1 = false;
            this.onPage2 = false;
            this.onPage3 = false;
            this.onPage4 = true;
        },
    },
    components: { NavigationHelper, RandomHelper, CustomHelper, FileHelper }
}
</script>

<style src="../assets/inputPages.css">

</style>