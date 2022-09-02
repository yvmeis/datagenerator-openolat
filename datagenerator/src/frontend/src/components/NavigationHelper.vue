<template>
  <div>
    <div class="tutorialtitle">
        Login and Logout
    </div>
    <p class="tutorialtext">
        The <span class="highlighted">URL</span> required to login is the <span class="highlighted">URL</span> of your OpenOLAT instances' RESTapi endpoint.<br>
        The <span class="highlighted">Username</span> can be any valid <span class="highlighted">Username</span> for your instance, but it is advised to use a SystemAdmin.<br>
        In order for the <span class="highlighted">Password</span> to be safe you should always logout after using the Datagenerator.<br>
        The <span class="highlighted">logout button</span> is situated at the top-right corner of the navigation bar.<br>
        You can not navigate the Datagenerator without <span class="highlighted">logging in</span>.
    </p>
    <div class="tutorialtitle">
        Pages
    </div>
    <p class="tutorialtext">
        The <span class="highlighted">RANDOM</span> page is used to generate random data for your OO instance.<br>
        The <span class="highlighted">CUSTOM</span> page is used to load premade or custom made templates onto your OO instance.<br>
        The <span class="highlighted">CLEANING</span> page is used to clean up your OO instance. You can only delete objects created by a Datagenerator.
    </p>
    <div class="tutorialtitle">
        Navigation Bar
    </div>
    <p class="tutorialtext">
        Use the navigation bar to navigate the Datagenerator.<br>
        It is not accessible until you log in.
    </p>
    <div class="tutorialtitle">
        Help
    </div>
    <p class="tutorialtext">
        The <span class="highlighted">Help</span> button at the bottom right of the corner is the entry to the Helppage.<br>
        Use the <span class="highlighted">grey navigation bar</span> to navigate the Helppage.<br>
        Click the collapsed tab to expand it again.
    </p>
  </div>
</template>

<script>
export default {

    name: 'NavigationHelper',

}
</script>

<style src="../assets/inputPages.css">

</style>