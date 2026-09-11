variable "db_password" {
  description = "Password for the FinSight RDS database"
  type        = string
  sensitive   = true
}

variable "jwt_secret" {
  description = "JWT signing secret for FinSight Cloud"
  type        = string
  sensitive   = true
}