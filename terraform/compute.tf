
resource "aws_instance" "finsight_app" {
  ami           = "ami-06033d1583f2e66ec"
  instance_type = "t3.micro"

  subnet_id                   = aws_subnet.public_subnet.id
  vpc_security_group_ids      = [aws_security_group.app_sg.id]
  associate_public_ip_address = true

  user_data = templatefile("${path.module}/user_data.sh", {
    db_host     = aws_db_instance.finsight_db.address
    db_username = "finsight_admin"
    db_password = var.db_password
    jwt_secret  = var.jwt_secret
  })

  tags = {
    Name = "finsight-app"
  }
}

resource "aws_eip" "finsight_app_eip" {
  instance = aws_instance.finsight_app.id

  tags = {
    Name = "finsight-app-eip"
  }
}