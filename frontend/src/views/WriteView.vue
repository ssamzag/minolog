<script setup lang="ts">
import {ref} from "vue";
import axios from 'axios';
import router from "@/router";

const title = ref("");
const content = ref("");

const write = function () {
  axios
      .post("/api/posts", {
        title: title.value,
        content: content.value
      }, {
        headers: {
            Authorization: 'bearer ' +  localStorage.getItem('token')
        }
      })
      .then(() => {
        router.replace({name: "home"})
      })
}

</script>

<template>
  <div>
    <el-input v-model="title" input type="text" placeholder="제목을 입력해 주세요"/>
  </div>
  <div>
    <div>
      <el-input v-model="content" type="textarea" rows="15"/>
    </div>
    <el-button type="primary" @click="write()">글 작성</el-button>
    <div></div>
  </div>

</template>

<style>

</style>
