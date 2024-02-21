@Library('jenkins-ci-automation@master') _

BlibliPipeline([
    type: "java11",
    sonar: [
      serverId: 'sonar9-gcp'
    ],

    build_config: [
        java11: [
            maven_opts_value: '-Xms1g -Xmx1g -XX:TieredStopAtLevel=1 -XX:+UseParallelGC',
            min_cpu: '4',
            max_cpu: '4',
            min_memory: '4Gi',
            max_memory: '4Gi'
        ]
    ],
    test: [
        integration: [
            mongo: [
                enabled : true,
                version : '4.2',
                username: 'mongo',
                password: 'mongo',
                min_cpu: '1',
                max_cpu: '1',
                min_memory: '1Gi',
                max_memory: '1Gi'
            ]
        ]
    ],
  	application : [
        tribe : "activation",
        squad : "enticement",
        service_name : "blibli-library"
    ]
])
