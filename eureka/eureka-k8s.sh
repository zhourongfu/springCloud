#!/bin/bash
domain=`hostname -f`
pod_name=`hostname`
pod_index=${pod_name: -1}
pod_length=${#pod_name}
pod_prefix=${pod_name: 0:-1}
domain_suffix=${domain: $pod_length}
echo "suffix="$domain_suffix
defaultZone=""
for((i=0;i<$REPLICAS;i++))
do
  if [[ $i != $pod_index ]]
  then
      if [[ $i == 0 ]]
      then
         defaultZone=$pod_prefix""$i""$domain_suffix
      else
         defaultZone=$defaultZone","$pod_prefix""$i""$domain_suffix
      fi
  fi
done
echo "set property eureka.client.serviceUrl.defaultZone="$defaultZone
export eureka.client.serviceUrl.defaultZone=$defaultZone