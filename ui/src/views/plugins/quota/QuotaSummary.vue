// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

<template>
  <div>
    <autogen-view
      @change-resource="changeResource"/>
    <quota-summary-resource
      v-if="isSummaryResouce"
      :resource="resource"
      :tabs="$route.meta.tabs"/>
  </div>
</template>

<script>
import AutogenView from '@/views/AutogenView.vue'
import QuotaSummaryResource from '@/views/plugins/quota/QuotaSummaryResource'

export default {
  name: 'QuotaSummary',
  components: {
    AutogenView,
    QuotaSummaryResource
  },
  data () {
    return {
      resource: {}
    }
  },
  provide: function () {
    return {
      parentChangeResource: this.changeResource
    }
  },
  computed: {
    isSummaryResouce () {
      if (this.$route.path.startsWith('/quotasummary')) {
        if (this.$route.query && 'quota' in this.$route.query && this.$route.query.quota) {
          return true
        }
      }
      return false
    }
  },
  methods: {
    changeResource (resource) {
      this.resource = resource
    }
  }
}
</script>
