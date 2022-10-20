#!/bin/bash

sleep 15

echo "Pre-populating records..."

pet1_id=1
pet2_id=2
vet1_id=1
vet2_id=2

vet1=$(curl -s -k -X POST http://localhost:4080/vets \
  -H 'Content-Type: application/json' \
  --data '
{
  "name": "Dog Doc",
  "specialties": ["doggos"]
}
')

vet1_id=$(echo $vet1 | jq ".id")

vet2=$(curl -s -k -X POST http://localhost:4080/vets \
  -H 'Content-Type: application/json' \
  --data '
{
  "name": "Meow Doc",
  "specialties": ["cats"]
}
')

vet2_id=$(echo $vet2 | jq ".id")

pet1=$(curl -s -k http://localhost:4080/pets \
  -H 'Content-Type: application/json' \
  --data '
{
 "name": "Mr Sprinkles II",
 "age": 5,
 "species": "CAT"
}
')

pet1_id=$(echo "$pet1" | jq ".id")

pet2=$(curl -s -k http://localhost:4080/pets \
  -H 'Content-Type: application/json' \
  --data '
{
 "name": "Boop",
 "age": 4,
 "species": "DOG"
}
')

pet2_id=$(echo "$pet2" | jq ".id")

curl -s -k http://localhost:4080/visits/pets/"${pet1_id}"/vets/"${vet1_id}" \
  -H 'Content-Type: application/json' \
  --data '
{
 "date": "2030-11-30T18:35:24.00Z",
 "treatment": "checkup"
}
'

curl -s -k http://localhost:4080/visits/pets/"${pet2_id}"/vets/"${vet2_id}" \
  -H 'Content-Type: application/json' \
  --data '
{
 "date": "2030-11-30T18:35:24.00Z",
 "treatment": "eyeballs"
}
'

function step_sleep () {
  sleep 5
}

echo "Running automated tests..."

while true; do
  curl -s -o /dev/null http://localhost:4080/pets
  step_sleep
  curl -s -o /dev/null http://localhost:4080/vets
  step_sleep
  curl -s -o /dev/null http://localhost:4080/pets/1
  step_sleep
  curl -s -o /dev/null http://localhost:4080/pets/2
  step_sleep
  curl -s -o /dev/null http://localhost:4080/vets/1
  step_sleep
  curl -s -o /dev/null http://localhost:4080/vets/2
  step_sleep
  curl -s -o /dev/null http://localhost:4080/visits/pets/1
  step_sleep
  curl -s -o /dev/null http://localhost:4080/visits/pets/2
  step_sleep
  curl -s -o /dev/null http://localhost:4080/clm/facts
  step_sleep
  curl -s -o /dev/null http://localhost:4080/clm/kotlin/facts
  step_sleep
  # Generate error
  curl -s -k http://localhost:4080/visits/pets/"${pet1_id}"/vets/"${vet1_id}" \
    -H 'Content-Type: application/json' \
    --data '
  {
     blah111
  }
  '
  step_sleep

  timestamp=$(date +"%F %T,%3N")
  echo "$timestamp Completed a full set of operations."

  # go too fast and the agent starts sampling
  sleep 10
done
