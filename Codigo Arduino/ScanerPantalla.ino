#include <Wire.h>        /*include Wire.h library for OLED display*/

void setup()

{
  Wire.begin();           /*I2C communication with OLED starts*/
  Serial.begin(9600);    /*baud rate defined for Serial Communication*/
  while (!Serial);       /*Wait for Serial output*/
  Serial.println("\nI2C Scanner");
}
void loop()
{
  byte err, adr;       /*variable error is defined with address of I2C*/
  int number_of_devices;
  Serial.println("Scanning.");
  number_of_devices = 0;
  for (adr = 1; adr < 127; adr++)
  {
    Wire.beginTransmission(adr);
    err = Wire.endTransmission();
    if (err == 0)
    {
      Serial.print("I2C device at address 0x");
      if (adr < 16)
        Serial.print("0");
      Serial.print(adr, HEX);
      Serial.println("  !");
      number_of_devices++;
    }
    else if (err == 4)
    {
      Serial.print("Unknown error at address 0x");
      if (adr < 16)
        Serial.print("0");
      Serial.println(adr, HEX);
    }
  }
  if (number_of_devices == 0)
    Serial.println("No I2C devices attached\n");
  else
    Serial.println("done\n");
  delay(5000);             /*wait 5 sec for next I2C scan*/
}