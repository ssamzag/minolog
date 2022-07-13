<script setup lang="ts">
import {defineProps, onMounted, ref} from "vue";
import axios from 'axios';
import {useRouter} from "vue-router";
const id = ref("");
const pw = ref("");

const router = useRouter();

const login = () => {
  axios.post(`/api/login/token`, {
    userId: id.value,
    password: pw.value
  }).then((response) => {
    localStorage.setItem('token', response.data.accessToken);
    router.replace({name: "home"});
  }).catch(r => {
    alert(r.response.data.message);
  });
}

</script>

<template>
  <div>
    <el-input v-model="id" type="text"/>
  </div>
  <div>
    <div>
      <el-input v-model="pw" type="password" rows="15"/>
    </div>
    <el-button type="warning" @click="login()">로그인</el-button>
    <div></div>
  </div>

</template>

<style>

</style>
