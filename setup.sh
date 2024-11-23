
#update dependencies
sudo apt-get update

#install docker
sudo apt-get install docker.io -y

#configure docker to start on boot
sudo systemctl enable docker

#install aws cli
sudo apt-get install awscli -y

#Sleep
sleep 10

#Getting thew rest of the variables from aws cli
# shellcheck disable=SC2154
DB_SECRET=$(sudo aws secretsmanager get-secret-value --secret-id "${DBSecret}" --query SecretString --output text)
DB_USERNAME=$(echo "$DB_SECRET" | jq -r .username)
DB_PASSWORD=$(echo "$DB_SECRET" | jq -r .password)

# Establishing the environment variables
echo "export DB_USERNAME=$DB_USERNAME" | sudo tee -a /etc/environment
echo "export DB_PASSWORD=$DB_PASSWORD" | sudo tee -a /etc/environment

#Sleep
sleep 10

#execute the docker-compose file
sudo docker-compose up -d


