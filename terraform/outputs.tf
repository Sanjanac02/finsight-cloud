output "rds_endpoint" {
  description = "RDS endpoint for FinSight Cloud"
  value       = aws_db_instance.finsight_db.address
}

output "ec2_public_ip" {
  description = "Elastic IP address of the FinSight application server"
  value       = aws_eip.finsight_app_eip.public_ip
}